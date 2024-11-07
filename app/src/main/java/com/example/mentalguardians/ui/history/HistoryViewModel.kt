package com.example.mentalguardians.ui.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.DetailMoodData
import com.example.mentalguardians.data.response.GetMoodData
import com.example.mentalguardians.data.response.GetMoodResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class HistoryUIState(
    val isLoading: Boolean = false,
    val listContent: List<GetMoodData> = emptyList(),
    val isLastPage: Boolean = false,
    val detailHistory: DetailMoodData = DetailMoodData(0, "", false, "", "")
)

class HistoryViewModel(private val apiClient: ApiClient): ViewModel() {
    var historyUIState by mutableStateOf(HistoryUIState())
        private set

    private var page = 1

    fun clearList(){
        historyUIState = historyUIState.copy(listContent = emptyList(), isLoading = false, isLastPage = false)
        page = 1
    }

    fun clearDetail(){
        historyUIState = historyUIState.copy(detailHistory = DetailMoodData(0, "", false, "", ""))
    }

    fun setDetail(id: Int, content: String, isGood: Boolean, date: String, time: String){
        val detailMoodData = DetailMoodData(id, content, isGood, date, time)
        historyUIState = historyUIState.copy(detailHistory = detailMoodData)
    }

    fun getAllHistory(onError: (String) -> Unit){
        viewModelScope.launch{
            historyUIState = historyUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getAllMoods(page = page, limit = 15)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        if(successResponse.data.isEmpty()){
                            historyUIState = historyUIState.copy(isLastPage = true)
                            return@launch
                        }

                        historyUIState = historyUIState.copy(
                            listContent = historyUIState.listContent + successResponse.data
                        )

                        page++
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, GetMoodResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("HistoryViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                historyUIState = historyUIState.copy(isLoading = false)
            }
        }
    }
}