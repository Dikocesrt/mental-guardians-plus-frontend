package com.example.mentalguardians.ui.therapist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.TherapistData
import com.example.mentalguardians.data.response.TherapistResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class TherapistUIState(
    val isLoading: Boolean = false,
    val listContent: List<TherapistData> = emptyList(),
    val isLastPage: Boolean = false
)

class TherapistViewModel(private val apiClient: ApiClient): ViewModel() {
    var therapistUIState by mutableStateOf(TherapistUIState())
        private set

    private var page = 1

    fun clearList(){
        therapistUIState = therapistUIState.copy(listContent = emptyList(), isLoading = false, isLastPage = false)
        page = 1
    }

    fun getAllTherapists(onError: (String) -> Unit, specialist: String){
        viewModelScope.launch{
            therapistUIState = therapistUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getAllTherapists(page = page, limit = 10, specialist = specialist)
                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        if(successResponse.data.isEmpty()){
                            therapistUIState = therapistUIState.copy(isLastPage = true)
                            return@launch
                        }

                        therapistUIState = therapistUIState.copy(
                            listContent = therapistUIState.listContent + successResponse.data
                        )

                        page++
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, TherapistResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("VideoViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                therapistUIState = therapistUIState.copy(isLoading = false)
            }
        }
    }
}