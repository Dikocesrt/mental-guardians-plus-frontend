package com.example.mentalguardians.ui.story

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.ContentData
import com.example.mentalguardians.data.response.ContentResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class DetailStoryUIState(
    val isLoading: Boolean = false,
    val storyData: ContentData = ContentData(0, "", "", "", "", "", "", "",)
)

class DetailStoryViewModel(private val apiClient: ApiClient): ViewModel() {
    var detailStoryUIState by mutableStateOf(DetailStoryUIState())
        private set

    fun getDetailStory(onError: (String) -> Unit, storyID: Int){
        viewModelScope.launch{
            detailStoryUIState = detailStoryUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getDetailStory(id = storyID)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        val storyData = ContentData(successResponse.data.id, successResponse.data.title, successResponse.data.author, successResponse.data.content, successResponse.data.category, successResponse.data.type, successResponse.data.thumbnailURL, successResponse.data.createdAt)
                        detailStoryUIState = detailStoryUIState.copy(storyData = storyData)
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
                detailStoryUIState = detailStoryUIState.copy(isLoading = false)
            }
        }
    }
}