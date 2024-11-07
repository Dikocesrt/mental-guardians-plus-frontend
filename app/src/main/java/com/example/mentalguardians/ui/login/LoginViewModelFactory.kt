package com.example.mentalguardians.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.utils.UserPreferences

class LoginViewModelFactory(
    private val userPreferences: UserPreferences,
    private val apiClient: ApiClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userPreferences, apiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
