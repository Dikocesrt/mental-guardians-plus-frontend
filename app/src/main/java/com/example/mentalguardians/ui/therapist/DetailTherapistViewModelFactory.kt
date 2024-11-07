package com.example.mentalguardians.ui.therapist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mentalguardians.data.retrofit.ApiClient

class DetailTherapistViewModelFactory(
    private val apiClient: ApiClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailTherapistViewModel::class.java)) {
            return DetailTherapistViewModel(apiClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}