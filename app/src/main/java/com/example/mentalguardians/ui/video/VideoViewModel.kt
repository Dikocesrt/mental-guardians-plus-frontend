package com.example.mentalguardians.ui.video

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.VideoData
import com.example.mentalguardians.data.response.VideoResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class VideoUIState(
    val isLoading: Boolean = false,
    val listVideo: List<VideoData> = emptyList(),
    val isLastPage: Boolean = false
)

class VideoViewModel(private val apiClient: ApiClient): ViewModel() {
    var videoUIState by mutableStateOf(VideoUIState())
        private set

    private var page = 1

    fun clearList(){
        videoUIState = videoUIState.copy(isLoading = false, isLastPage = false, listVideo = emptyList())
        page = 1
    }

    fun getAllVideos(onError: (String) -> Unit, categoryVideos: String){
        viewModelScope.launch{
            videoUIState = videoUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getAllVideos(page = page, limit = 10, category = categoryVideos)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        if(successResponse.data.isEmpty()){
                            videoUIState = videoUIState.copy(isLastPage = true)
                            return@launch
                        }

                        videoUIState = videoUIState.copy(
                            listVideo = videoUIState.listVideo + successResponse.data
                        )

                        page++
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, VideoResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("VideoViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                videoUIState = videoUIState.copy(isLoading = false)
            }
        }
    }
}