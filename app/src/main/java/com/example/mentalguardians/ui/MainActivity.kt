package com.example.mentalguardians.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.ui.login.LoginScreen
import com.example.mentalguardians.ui.login.LoginViewModel
import com.example.mentalguardians.ui.login.LoginViewModelFactory
import com.example.mentalguardians.ui.main.MainScreen
import com.example.mentalguardians.ui.main.MainViewModel
import com.example.mentalguardians.ui.onboard.FirstOnBoardScreen
import com.example.mentalguardians.ui.onboard.SecondOnBoardScreen
import com.example.mentalguardians.ui.onboard.ThirdOnBoardScreen
import com.example.mentalguardians.ui.signup.SignUpScreen
import com.example.mentalguardians.ui.signup.SignUpViewModel
import com.example.mentalguardians.ui.signup.SignUpViewModelFactory
import com.example.mentalguardians.ui.splash.SplashScreen
import com.example.mentalguardians.ui.theme.MentalGuardiansTheme
import com.example.mentalguardians.utils.Screen
import com.example.mentalguardians.utils.UserPreferences
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainActivityViewModel: MainActivityViewModel = viewModel()
            val navController = rememberNavController()
            val userPreferences = UserPreferences(this)
            val apiClient = ApiClient(userPreferences)
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(userPreferences, apiClient))
            val signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory(userPreferences, apiClient))
            val mainViewModel: MainViewModel = viewModel()
            MentalGuardiansTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ){
                        composable(Screen.SplashScreen.route) {
                            if (mainActivityViewModel.mainActivityState.isSplashVisible) {
                                SplashScreen()
                            } else {
                                navController.navigate(mainActivityViewModel.mainActivityState.initialRoute) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }
                        }
                        composable(Screen.FirstOnBoardScreen.route){
                            FirstOnBoardScreen(
                                navigateToSecondScreen = {
                                    navController.navigate(Screen.SecondOnBoardScreen.route)
                                },
                                navigateToThirdScreen = {
                                    navController.navigate(Screen.ThirdOnBoardScreen.route)
                                }
                            )
                        }
                        composable(Screen.SecondOnBoardScreen.route){
                            SecondOnBoardScreen(
                                navigateToThirdScreen = {
                                    navController.navigate(Screen.ThirdOnBoardScreen.route)
                                }
                            )
                        }
                        composable(Screen.ThirdOnBoardScreen.route){
                            ThirdOnBoardScreen(
                                navigateToLoginScreen = {
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                            )
                        }
                        composable(Screen.LoginScreen.route){
                            LoginScreen(
                                navigateToSignUpScreen = {
                                    navController.navigate(Screen.SignupScreen.route)
                                },
                                navigateToMainScreen = {
                                    navController.navigate(Screen.MainScreen.route)
                                },
                                loginViewModel = loginViewModel
                            )
                        }
                        composable(Screen.SignupScreen.route){
                            SignUpScreen(
                                navigateToLoginScreen = {
                                    navController.navigate(Screen.LoginScreen.route)
                                },
                                navigateToMainScreen = {
                                    navController.navigate(Screen.MainScreen.route)
                                },
                                signUpViewModel = signUpViewModel
                            )
                        }
                        composable(Screen.MainScreen.route){
                            mainViewModel.setCurrentScreen(Screen.BottomScreen.Home)
                            MainScreen(mainViewModel = mainViewModel, apiClient = apiClient, userPreferences = userPreferences, loginViewModel = loginViewModel, signUpViewModel = signUpViewModel)
                        }
                    }

                    LaunchedEffect(Unit) {
                        val token = userPreferences.token.first()
                        if (token != null) {
                            mainActivityViewModel.onInitialRouteChange(Screen.MainScreen.route)
                        } else {
                            mainActivityViewModel.onInitialRouteChange(Screen.FirstOnBoardScreen.route)
                        }
                        navController.navigate(mainActivityViewModel.mainActivityState.initialRoute) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                        mainActivityViewModel.onSplashVisibilityChange(false)
                    }
                }
            }
        }
    }
}
