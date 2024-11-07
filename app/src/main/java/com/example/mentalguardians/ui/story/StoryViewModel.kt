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

data class StoryUIState(
    val isLoading: Boolean = false,
    val listContent: List<ContentData> = emptyList(),
    val isLastPage: Boolean = false
)

class StoryViewModel(private val apiClient: ApiClient): ViewModel() {
    var storyUIState by mutableStateOf(StoryUIState())
        private set

    private var page = 1

    fun clearList(){
        storyUIState = storyUIState.copy(isLoading = false, listContent = emptyList(), isLastPage = false)
        page = 1
    }

    fun getAllStories(onError: (String) -> Unit, category: String){
        viewModelScope.launch{
            storyUIState = storyUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getAllStories(page = page, limit = 10, category = category)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        if(successResponse.data.isEmpty()){
                            storyUIState = storyUIState.copy(isLastPage = true)
                            return@launch
                        }

                        storyUIState = storyUIState.copy(
                            listContent = storyUIState.listContent + successResponse.data
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
                storyUIState = storyUIState.copy(isLoading = false)
            }
        }
    }
}