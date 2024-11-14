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

data class ArticleUIState(
    val isLoading: Boolean = false,
    val listContent: List<ContentData> = emptyList(),
    val isLastPage: Boolean = false,
    val isLoadingLoadMore: Boolean = false
)

class ArticleViewModel(private val apiClient: ApiClient): ViewModel() {
    var articleUIState by mutableStateOf(ArticleUIState())
        private set

    private var page = 1

    fun removeAllList() {
        articleUIState = articleUIState.copy(listContent = emptyList(), isLoading = false, isLastPage = false)
        page = 1
        Log.d("ArticleViewModel", "Clearing List and resetting page to 1")
    }

    fun getAllArticles(onError: (String) -> Unit, category: String) {
        Log.d("ArticleViewModel", "getAllArticles called with category: $category")
        viewModelScope.launch {
            if (page == 1){
                articleUIState = articleUIState.copy(isLoading = true)
            }else{
                articleUIState = articleUIState.copy(isLoadingLoadMore = true)
            }
            try {
                val response = apiClient.retrofitService.getAllArticles(page = page, limit = 10, category = category)
                if (response.isSuccessful) {
                    val successResponse = response.body()
                    if (successResponse != null) {
                        if (successResponse.data.isEmpty()) {
                            articleUIState = articleUIState.copy(isLastPage = true)
                            Log.d("ArticleViewModel", "Reached last page")
                            return@launch
                        }

                        articleUIState = articleUIState.copy(
                            listContent = articleUIState.listContent + successResponse.data
                        )

                        Log.d("ArticleViewModel", "List Size = ${articleUIState.listContent.size}")

                        page++
                    } else {
                        onError("Response body is null")
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorContent = Gson().fromJson(errorResponse, ContentResponse::class.java)
                        onError(errorContent.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            } catch (e: Exception) {
                Log.e("ArticleViewModel", "Exception in getAllArticles: ${e.localizedMessage}")
                onError(e.localizedMessage ?: "Error occurred")
            } finally {
                articleUIState = articleUIState.copy(isLoading = false)
                articleUIState = articleUIState.copy(isLoadingLoadMore = false)
                Log.d("ArticleViewModel", "Loading finished")
            }
        }
    }

}
