package com.example.mentalguardians.ui.music

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
    val isLastPage: Boolean = false
)

class MusicViewModel(private val apiClient: ApiClient): ViewModel() {
    var musicUIState by mutableStateOf(MusicUIState())
        private set

    private var page = 1

    fun getAllMusics(onError: (String) -> Unit){
        viewModelScope.launch{
            musicUIState = musicUIState.copy(isLoading = true)
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
            }
        }
    }
}