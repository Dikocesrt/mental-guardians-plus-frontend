package com.example.mentalguardians.ui.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.request.SignUpRequest
import com.example.mentalguardians.data.response.LoginSignUpResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.utils.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class SignUpUIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
)

class SignUpViewModel(private val userPreferences: UserPreferences, private val apiClient: ApiClient): ViewModel() {
    var signUpUIState by mutableStateOf(SignUpUIState())
        private set

    fun onFirstNameChange(newName: String){
        signUpUIState = signUpUIState.copy(firstName = newName)
    }

    fun onLastNameChange(newName: String){
        signUpUIState = signUpUIState.copy(lastName = newName)
    }

    fun onEmailChange(newEmail: String){
        signUpUIState = signUpUIState.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String){
        signUpUIState = signUpUIState.copy(password = newPassword)
    }

    fun togglePasswordVisibility(){
        signUpUIState = signUpUIState.copy(isPasswordVisible = !signUpUIState.isPasswordVisible)
    }

    fun registerUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            signUpUIState = signUpUIState.copy(isLoading = true)
            try {
                val request = SignUpRequest(
                    email = signUpUIState.email,
                    password = signUpUIState.password,
                    firstName = signUpUIState.firstName,
                    lastName = signUpUIState.lastName
                )
                val response = apiClient.retrofitService.registerUser(request)

                if (response.isSuccessful) {
                    val successResponse = response.body()
                    if (successResponse != null) {
                        userPreferences.saveToken(successResponse.data.token)
                        onSuccess()
                    } else {
                        onError("Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val errorResponse = Gson().fromJson(errorBody, LoginSignUpResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.d("SignUpViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                signUpUIState = signUpUIState.copy(isLoading = false)
            }
        }
    }
}