package com.example.mentalguardians.utils

import androidx.annotation.DrawableRes
import com.example.mentalguardians.R

sealed class Screen(val title: String, val route: String) {

    sealed class BottomScreen(val bTitle: String, val bRoute: String, @DrawableRes val icon: Int): Screen(bTitle, bRoute){
        object Home: BottomScreen("Beranda", "beranda", R.drawable.ic_home)
        object History: BottomScreen("Riwayat", "riwayat", R.drawable.ic_folder)
        object Therapist: BottomScreen("Psikolog", "terapis/{category}", R.drawable.ic_therapist){
            fun createRoute(category: String) = "terapis/$category"
        }
        object Profile: BottomScreen("Profil", "profil", R.drawable.ic_profile)
    }

    object FirstOnBoardScreen: Screen("First OnBoard", "firstonboardscreen")
    object SecondOnBoardScreen: Screen("Second OnBoard", "secondonboardscreen")
    object ThirdOnBoardScreen: Screen("Third OnBoard", "thirdonboardscreen")
    object LoginScreen: Screen("Masuk", "loginscreen")
    object SignupScreen: Screen("Daftar", "signupscreen")
    object MainScreen: Screen("Main", "mainscreen")
    object SplashScreen: Screen("Splash", "splashscreen")
    object VideoScreen: Screen("Video", "videoscreen/{category}"){
        fun createRoute(category: String) = "videoscreen/$category"
    }
    object ArticleScreen: Screen("Artikel", "articlescreen/{category}"){
        fun createRoute(category: String) = "articlescreen/$category"
    }
    object StoryScreen: Screen("Cerita Inspiratif", "storyscreen/{category}"){
        fun createRoute(category: String) = "storyscreen/$category"
    }
    object MusicScreen: Screen("Musik", "musicscreen")
    object DetailTherapistScreen: Screen("Detail Psikolog", "detailtherapistscreen/{id}"){
        fun createRoute(id: Int) = "detailtherapistscreen/$id"
    }
    object DetailArticleScreen: Screen("Detail Artikel", "detailarticlescreen/{id}"){
        fun createRoute(id: Int) = "detailarticlescreen/$id"
    }
    object DetailStoryScreen: Screen("Detail Cerita", "detailstoryscreen/{id}"){
        fun createRoute(id: Int) = "detailstoryscreen/$id"
    }
    object BadMoodScreen: Screen("Hasil Analisis", "badmoodscreen")
    object GoodMoodScreen: Screen("Hasil Analisis", "goodmoodscreen")
}

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.History,
    Screen.BottomScreen.Therapist,
    Screen.BottomScreen.Profile
)