package com.example.mentalguardians.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalguardians.data.request.LoginRequest
import com.example.mentalguardians.data.response.LoginSignUpResponse
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.utils.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.launch

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
)

class LoginViewModel(private val userPreferences: UserPreferences, private val apiClient: ApiClient): ViewModel(){
    var loginUIState by mutableStateOf(LoginUIState())
        private set

    fun onEmailChange(newEmail: String){
        loginUIState = loginUIState.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String){
        loginUIState = loginUIState.copy(password = newPassword)
    }

    fun togglePasswordVisibility(){
        loginUIState = loginUIState.copy(isPasswordVisible = !loginUIState.isPasswordVisible)
    }

    fun loginUser(onSuccess: () -> Unit, onError: (String) -> Unit){
        viewModelScope.launch{
            loginUIState = loginUIState.copy(isLoading = true)
            val startTime = System.currentTimeMillis()
            try {
                val request = LoginRequest(
                    email = loginUIState.email,
                    password = loginUIState.password
                )

                val response = apiClient.retrofitService.loginUser(request)

                if (response.isSuccessful){
                    val successResponse = response.body()
                    if(successResponse != null){
                        userPreferences.saveToken(successResponse.data.token)
                        onSuccess()
                    } else {
                        onError("Response body is null")
                    }
                }else{
                    val errorResponse = response.errorBody()?.string()
                    if (errorResponse != null) {
                        val errorResponse = Gson().fromJson(errorResponse, LoginSignUpResponse::class.java)
                        onError(errorResponse.message)
                    } else {
                        onError("Unknown error occurred")
                    }
                }
            }catch (e: Exception){
                e.message?.let { Log.d("LoginViewModel", it) }
                onError(e.localizedMessage ?: "Error occurred")
            }finally {
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                Log.d("LoginViewModel", "Waktu Respons Login: ${duration}ms")
                loginUIState = loginUIState.copy(isLoading = false)
            }
        }
    }
}