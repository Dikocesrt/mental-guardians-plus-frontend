package com.example.mentalguardians.ui.article

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

data class DetailArticleUIState(
    val isLoading: Boolean = false,
    val articleData: ContentData = ContentData(0, "", "", "", "", "", "", "",)
)

class DetailArticleViewModel(private val apiClient: ApiClient): ViewModel() {
    var detailArticleUIState by mutableStateOf(DetailArticleUIState())
        private set

    fun getDetailArticle(onError: (String) -> Unit, articleID: Int){
        viewModelScope.launch{
            detailArticleUIState = detailArticleUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getDetailArticle(id = articleID)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        val articleData = ContentData(successResponse.data.id, successResponse.data.title, successResponse.data.author, successResponse.data.content, successResponse.data.category, successResponse.data.type, successResponse.data.thumbnailURL, successResponse.data.createdAt)
                        detailArticleUIState = detailArticleUIState.copy(articleData = articleData)
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
                detailArticleUIState = detailArticleUIState.copy(isLoading = false)
            }
        }
    }
}