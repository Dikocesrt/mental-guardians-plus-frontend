package com.example.mentalguardians.ui.therapist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mentalguardians.data.retrofit.ApiClient

class TherapistViewModelFactory(
    private val apiClient: ApiClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TherapistViewModel::class.java)) {
            return TherapistViewModel(apiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}