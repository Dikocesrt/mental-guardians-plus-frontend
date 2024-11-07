package com.example.mentalguardians.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mentalguardians.utils.Screen

class MainViewModel: ViewModel(){
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)
        private set

    var title: MutableState<String> = mutableStateOf("")
        private set

    var previousScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)
        private set

    var modalBottomVisibility: MutableState<Boolean> = mutableStateOf(false)
        private set

    fun onModalBottomVisibilityChange(value: Boolean){
        modalBottomVisibility.value = value
    }

    fun setCurrentScreen(newScreen: Screen){
        currentScreen.value = newScreen
    }

    fun setPreviousScreen(screen: Screen){
        previousScreen.value = screen
    }

    fun setTitle(newTitle: String){
        title.value = newTitle
    }
}