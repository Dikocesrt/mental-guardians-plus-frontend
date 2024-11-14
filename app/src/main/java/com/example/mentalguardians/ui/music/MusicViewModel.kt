package com.example.mentalguardians.ui.music

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.ContentResponse
import com.example.mentalguardians.data.response.MusicData
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class MusicUIState(
    val isLoading: Boolean = false,
    val listContent: List<MusicData> = emptyList(),
    val isLastPage: Boolean = false,
    val mediaPlayer: MediaPlayer? = null,
    val currentPlayingId: Int? = null,
    val isLoadingLoadMore: Boolean = false
)

class MusicViewModel(private val apiClient: ApiClient): ViewModel() {

    companion object {
        // Volatile ensures visibility of changes across threads
        @Volatile
        private var instance: MusicViewModel? = null

        // Method to get the singleton instance
        fun getInstance(apiClient: ApiClient): MusicViewModel =
            instance ?: synchronized(this) {
                instance ?: MusicViewModel(apiClient).also { instance = it }
            }
    }

    var currentPlayingId: Int? = null
    var mediaPlayer: MediaPlayer? = null
    var musicUIState by mutableStateOf(MusicUIState())
        private set

    private var page = 1

    fun playMusic(musicUrl: String, itemId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(musicUrl)
            setOnPreparedListener {
                start()
            }
            setOnCompletionListener {
                currentPlayingId = null
                musicUIState = musicUIState.copy(mediaPlayer = mediaPlayer, currentPlayingId = currentPlayingId)
            }
            prepareAsync()
        }
        currentPlayingId = itemId
        musicUIState = musicUIState.copy(mediaPlayer = mediaPlayer, currentPlayingId = currentPlayingId)
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
        currentPlayingId = null
        musicUIState = musicUIState.copy(mediaPlayer = mediaPlayer, currentPlayingId = currentPlayingId)

    }
    fun getAllMusics(onError: (String) -> Unit){
        viewModelScope.launch{
            if (page == 1){
                musicUIState = musicUIState.copy(isLoading = true)
            }else{
                musicUIState = musicUIState.copy(isLoadingLoadMore = true)
            }
            try {
                val response = apiClient.retrofitService.getAllMusics(page = page, limit = 10)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        if(successResponse.data.isEmpty()){
                            musicUIState = musicUIState.copy(isLastPage = true)
                            return@launch
                        }

                        musicUIState = musicUIState.copy(
                            listContent = musicUIState.listContent + successResponse.data
                        )

                        page++
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, ContentResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("VideoViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                musicUIState = musicUIState.copy(isLoading = false)
                musicUIState = musicUIState.copy(isLoadingLoadMore = false)
            }
        }
    }
}