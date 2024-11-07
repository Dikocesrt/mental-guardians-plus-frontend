package com.example.mentalguardians.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.utils.UserPreferences

class SignUpViewModelFactory(
    private val userPreferences: UserPreferences,
    private val apiClient: ApiClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(userPreferences, apiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}