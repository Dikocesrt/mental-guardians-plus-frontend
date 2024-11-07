package com.example.mentalguardians.ui.mood

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BadMoodViewModel: ViewModel() {
    var selectedOption = mutableStateOf<String?>(null)
        private set

    fun onSelectedOptionChange(selected: String?){
        selectedOption.value = selected
    }
}