package com.example.mentalguardians.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CheckLoginStatus(userPreferences: UserPreferences, navigate: (String) -> Unit) {
    val token by userPreferences.token.collectAsState(initial = null)

    LaunchedEffect(token) {
        if (token != null) {
            navigate(Screen.MainScreen.route)
        } else {
            navigate(Screen.FirstOnBoardScreen.route)
        }
    }
}