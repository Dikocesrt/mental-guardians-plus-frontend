package com.example.mentalguardians.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.response.ProfileData
import com.example.mentalguardians.data.response.ProfileResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class ProfileUIState(
    val isLoading: Boolean = false,
    val dataProfile: ProfileData = ProfileData(0, "", "", "")
)

class ProfileViewModel(private val apiClient: ApiClient): ViewModel(){
    var profileUIState by mutableStateOf(ProfileUIState())
        private set

    fun getProfile(onError: (String) -> Unit){
        viewModelScope.launch {
            profileUIState = profileUIState.copy(isLoading = true)
            try {
                val response = apiClient.retrofitService.getProfile()

                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        val dataProfile = ProfileData(id = successResponse.data.id, email = successResponse.data.email, firstName = successResponse.data.firstName, lastName = successResponse.data.lastName)
                        profileUIState = profileUIState.copy(dataProfile = dataProfile)
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
                profileUIState = profileUIState.copy(isLoading = false)
            }
        }
    }
}