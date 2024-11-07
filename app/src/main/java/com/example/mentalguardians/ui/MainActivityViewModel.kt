package com.example.mentalguardians.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mentalguardians.utils.Screen

data class MainActivityState(
    val isSplashVisible: Boolean = true,
    val initialRoute: String = Screen.FirstOnBoardScreen.route
)

class MainActivityViewModel: ViewModel() {
    var mainActivityState by mutableStateOf(MainActivityState())
        private set

    fun onSplashVisibilityChange(visibility: Boolean){
        mainActivityState = mainActivityState.copy(isSplashVisible = visibility)
    }

    fun onInitialRouteChange(route: String){
        mainActivityState = mainActivityState.copy(initialRoute = route)
    }
}