package com.example.mentalguardians.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.request.MoodRequest
import com.example.mentalguardians.data.response.MoodResponse
import com.example.mentalguardians.data.response.ProfileResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class HomeUIState(
    val name: String = "Guest",
    val isLoading: Boolean = false,
    val isLoading2: Boolean = false,
    val isShowDialog: Boolean = false,
    val moodInput: String = ""
)

class HomeViewModel(private val apiClient: ApiClient) : ViewModel(){
    var HomeUIState by mutableStateOf(HomeUIState())
        private set

    fun changeShowDialog(value: Boolean){
        HomeUIState = HomeUIState.copy(isShowDialog = value)
    }

    fun onMoodInputChange(mood: String){
        HomeUIState = HomeUIState.copy(moodInput = mood)
    }

    fun sendMood(onSuccess: (Boolean) -> Unit, onError: (String) -> Unit){
        viewModelScope.launch {
            HomeUIState = HomeUIState.copy(isLoading2 = true)
            try {
                val request = MoodRequest(
                    content = HomeUIState.moodInput
                )

                val response = apiClient.retrofitService.sendMood(request = request)

                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        onSuccess(successResponse.data.isGood)
                    } else {
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, MoodResponse::class.java)
                        onError(errorResponse.message)
                        Log.d("HomeViewModel", errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("LoginViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                HomeUIState = HomeUIState.copy(isLoading2 = false)
            }
        }
    }

    fun getProfile(onError: (String) -> Unit){
        viewModelScope.launch {
            HomeUIState = HomeUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getProfile()

                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        HomeUIState = HomeUIState.copy(name = successResponse.data.firstName + " " + successResponse.data.lastName)
                    }else{
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, ProfileResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                HomeUIState = HomeUIState.copy(isLoading = false)
            }
        }
    }
}