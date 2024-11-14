package com.example.mentalguardians.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mentalguardians.R
import com.example.mentalguardians.data.retrofit.ApiClient
import com.example.mentalguardians.ui.article.ArticleScreen
import com.example.mentalguardians.ui.article.ArticleViewModel
import com.example.mentalguardians.ui.article.ArticleViewModelFactory
import com.example.mentalguardians.ui.article.DetailArticleScreen
import com.example.mentalguardians.ui.article.DetailArticleViewModel
import com.example.mentalguardians.ui.article.DetailArticleViewModelFactory
import com.example.mentalguardians.ui.history.HistoryScreen
import com.example.mentalguardians.ui.history.HistoryViewModel
import com.example.mentalguardians.ui.history.HistoryViewModelFactory
import com.example.mentalguardians.ui.home.HomeScreen
import com.example.mentalguardians.ui.home.HomeViewModel
import com.example.mentalguardians.ui.home.HomeViewModelFactory
import com.example.mentalguardians.ui.login.LoginScreen
import com.example.mentalguardians.ui.login.LoginViewModel
import com.example.mentalguardians.ui.mood.BadMoodScreen
import com.example.mentalguardians.ui.mood.BadMoodViewModel
import com.example.mentalguardians.ui.mood.GoodMoodScreen
import com.example.mentalguardians.ui.music.MusicScreen
import com.example.mentalguardians.ui.music.MusicViewModel
import com.example.mentalguardians.ui.music.MusicViewModelFactory
import com.example.mentalguardians.ui.profile.ProfileScreen
import com.example.mentalguardians.ui.profile.ProfileViewModel
import com.example.mentalguardians.ui.profile.ProfileViewModelFactory
import com.example.mentalguardians.ui.signup.SignUpScreen
import com.example.mentalguardians.ui.signup.SignUpViewModel
import com.example.mentalguardians.ui.story.DetailStoryScreen
import com.example.mentalguardians.ui.story.DetailStoryViewModel
import com.example.mentalguardians.ui.story.DetailStoryViewModelFactory
import com.example.mentalguardians.ui.story.StoryScreen
import com.example.mentalguardians.ui.story.StoryViewModel
import com.example.mentalguardians.ui.story.StoryViewModelFactory
import com.example.mentalguardians.ui.theme.poppinsFontFamily
import com.example.mentalguardians.ui.therapist.DetailTherapistScreen
import com.example.mentalguardians.ui.therapist.DetailTherapistViewModel
import com.example.mentalguardians.ui.therapist.DetailTherapistViewModelFactory
import com.example.mentalguardians.ui.therapist.TherapistScreen
import com.example.mentalguardians.ui.therapist.TherapistViewModel
import com.example.mentalguardians.ui.therapist.TherapistViewModelFactory
import com.example.mentalguardians.ui.video.VideoScreen
import com.example.mentalguardians.ui.video.VideoViewModel
import com.example.mentalguardians.ui.video.VideoViewModelFactory
import com.example.mentalguardians.utils.Screen
import com.example.mentalguardians.utils.UserPreferences
import com.example.mentalguardians.utils.screensInBottom

@Composable
fun BottomBar(mainViewModel: MainViewModel, controller: NavController, currentRoute: String?){
    if(mainViewModel.currentScreen.value is Screen.BottomScreen && !mainViewModel.modalBottomVisibility.value){
        BottomNavigation(
            modifier = Modifier.wrapContentSize(),
            backgroundColor = Color.White
        ) {
            screensInBottom.forEach { item->
                val tint = if(currentRoute == item.bRoute) colorResource(id = R.color.bottom_item_selected) else colorResource(id = R.color.bottom_item_unselected)
                BottomNavigationItem(
                    selected = currentRoute == item.bRoute,
                    onClick = {
                        controller.navigate(item.bRoute)
                        mainViewModel.setPreviousScreen(mainViewModel.currentScreen.value)
                        mainViewModel.setCurrentScreen(item)
                        mainViewModel.setTitle(item.bTitle)
                    },
                    icon = {
                        Icon(painter = painterResource(id = item.icon), contentDescription = null, tint = tint)
                    },
                    label = {
                        Text(
                            text = item.bTitle,
                            color = tint,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    selectedContentColor = colorResource(id = R.color.bottom_item_selected),
                    unselectedContentColor = colorResource(id = R.color.bottom_item_unselected)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(mainViewModel: MainViewModel, controller: NavController){
    if(mainViewModel.currentScreen.value != Screen.BottomScreen.Home && mainViewModel.currentScreen.value != Screen.BottomScreen.History && mainViewModel.currentScreen.value != Screen.BottomScreen.Profile && mainViewModel.currentScreen.value != Screen.LoginScreen && mainViewModel.currentScreen.value != Screen.SignupScreen){
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White // Set the desired background color here
            ),
            title = {
                Text(
                    text = mainViewModel.title.value,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF002055)
                )
            },
            navigationIcon = {
                if (mainViewModel.currentScreen.value != Screen.BottomScreen.History) {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color(0xFF002055)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun MainNavigation(
    navController: NavController,
    mainViewModel: MainViewModel,
    pd: PaddingValues,
    apiClient: ApiClient,
    userPreferences: UserPreferences,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel
){
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(apiClient = apiClient))
    val therapistViewModel: TherapistViewModel = viewModel(factory = TherapistViewModelFactory(apiClient = apiClient))
    val videoViewModel: VideoViewModel = viewModel(factory = VideoViewModelFactory(apiClient = apiClient))
    val articleViewModel: ArticleViewModel = viewModel(factory = ArticleViewModelFactory(apiClient = apiClient))
    val storyViewModel: StoryViewModel = viewModel(factory = StoryViewModelFactory(apiClient = apiClient))
    val musicViewModel: MusicViewModel = MusicViewModel.getInstance(apiClient = apiClient)
    val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(apiClient = apiClient))
    val detailTherapistViewModel: DetailTherapistViewModel = viewModel(factory = DetailTherapistViewModelFactory(apiClient = apiClient))
    val detailArticleViewModel: DetailArticleViewModel = viewModel(factory = DetailArticleViewModelFactory(apiClient = apiClient))
    val detailStoryViewModel: DetailStoryViewModel = viewModel(factory = DetailStoryViewModelFactory(apiClient = apiClient))
    val badMoodViewModel: BadMoodViewModel = viewModel()
    val historyViewModel: HistoryViewModel = viewModel(factory = HistoryViewModelFactory(apiClient = apiClient))

    NavHost(navController = navController as NavHostController, startDestination = Screen.BottomScreen.Home.bRoute){
        composable(Screen.LoginScreen.route){
            mainViewModel.setCurrentScreen(Screen.LoginScreen)
            mainViewModel.setTitle(Screen.LoginScreen.title)
            LoginScreen(
                navigateToSignUpScreen = {
                    navController.navigate(Screen.SignupScreen.route)
                },
                navigateToMainScreen = {
                    navController.navigate(Screen.BottomScreen.Home.bRoute)
                },
                loginViewModel = loginViewModel
            )
        }
        composable(Screen.SignupScreen.route){
            mainViewModel.setCurrentScreen(Screen.SignupScreen)
            mainViewModel.setTitle(Screen.SignupScreen.title)
            SignUpScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                navigateToMainScreen = {
                    navController.navigate(Screen.BottomScreen.Home.bRoute)
                },
                signUpViewModel = signUpViewModel
            )
        }
        composable(Screen.MainScreen.route){
            mainViewModel.setCurrentScreen(Screen.BottomScreen.Home)
            mainViewModel.setTitle(Screen.BottomScreen.Home.bTitle)
            MainScreen(mainViewModel = mainViewModel, apiClient = apiClient, userPreferences = userPreferences, loginViewModel = loginViewModel, signUpViewModel = signUpViewModel)
        }

        composable(Screen.BottomScreen.Home.bRoute){
            mainViewModel.setCurrentScreen(Screen.BottomScreen.Home)
            mainViewModel.setTitle(Screen.BottomScreen.Home.bTitle)
            HomeScreen(
                homeViewModel = homeViewModel,
                navigateToVideoScreen = {
                    navController.navigate(Screen.VideoScreen.route)
                },
                navigateToArticleScreen = {
                    navController.navigate(Screen.ArticleScreen.route)
                },
                navigateToStoryScreen = {
                    navController.navigate(Screen.StoryScreen.route)
                },
                navigateToMusicScreen = {
                    navController.navigate(Screen.MusicScreen.route)
                },
                navigateToBadMoodScreen = {
                    navController.navigate(Screen.BadMoodScreen.route)
                },
                navigateToGoodMoodScreen = {
                    navController.navigate(Screen.GoodMoodScreen.route)
                }
            )
        }
        composable(Screen.BottomScreen.History.bRoute){
            mainViewModel.setCurrentScreen(Screen.BottomScreen.History)
            mainViewModel.setTitle(Screen.BottomScreen.History.bTitle)
            HistoryScreen(historyViewModel = historyViewModel, mainViewModel = mainViewModel)
        }
        composable(
            route = Screen.BottomScreen.Therapist.bRoute,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ){backStackEntry->
            val category = backStackEntry.arguments?.getString("category").let {
                if (it.isNullOrBlank() || it == "{category}") "" else it
            }
            mainViewModel.setCurrentScreen(Screen.BottomScreen.Therapist)
            mainViewModel.setTitle(Screen.BottomScreen.Therapist.bTitle)

            backStackEntry.arguments?.remove("category")

            TherapistScreen(
                therapistViewModel = therapistViewModel,
                specialist = category,
                navigateToDetailTherapistScreen = {id->
                    navController.navigate(Screen.DetailTherapistScreen.createRoute(id))
                }
            )
        }
        composable(Screen.BottomScreen.Profile.bRoute){
            mainViewModel.setCurrentScreen(Screen.BottomScreen.Profile)
            mainViewModel.setTitle(Screen.BottomScreen.Profile.bTitle)
            ProfileScreen(
                profileViewModel = profileViewModel,
                userPreferences = userPreferences,
                navigateToLoginScreen = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
//                    (context as? Activity)?.finishAffinity()
                }
            )
        }
        composable(
            route = Screen.VideoScreen.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ){backStackEntry->
            val category = backStackEntry.arguments?.getString("category").let {
                if (it.isNullOrBlank() || it == "{category}") "" else it
            }
            mainViewModel.setCurrentScreen(Screen.VideoScreen)
            mainViewModel.setTitle(Screen.VideoScreen.title)

            backStackEntry.arguments?.remove("category")

            VideoScreen(videoViewModel = videoViewModel, categoryVideos = category)
        }
        composable(
            route = Screen.ArticleScreen.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ){backStackEntry->
            val category = backStackEntry.arguments?.getString("category").let {
                if (it.isNullOrBlank() || it == "{category}") "" else it
            }
            mainViewModel.setCurrentScreen(Screen.ArticleScreen)
            mainViewModel.setTitle(Screen.ArticleScreen.title)

            backStackEntry.arguments?.remove("category")

            ArticleScreen(
                articleViewModel = articleViewModel,
                categoryArticles = category,
                navigateToDetailArticleScreen = {id->
                    navController.navigate(Screen.DetailArticleScreen.createRoute(id))
                }
            )
        }
        composable(
            route = Screen.StoryScreen.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ){backStackEntry->
            val category = backStackEntry.arguments?.getString("category").let {
                if (it.isNullOrBlank() || it == "{category}") "" else it
            }
            mainViewModel.setCurrentScreen(Screen.StoryScreen)
            mainViewModel.setTitle(Screen.StoryScreen.title)

            backStackEntry.arguments?.remove("category")

            StoryScreen(
                storyViewModel = storyViewModel,
                categoryStories = category,
                navigateToDetailStoryScreen = {id->
                    navController.navigate(Screen.DetailStoryScreen.createRoute(id))
                }
            )
        }
        composable(Screen.MusicScreen.route){
            mainViewModel.setCurrentScreen(Screen.MusicScreen)
            mainViewModel.setTitle(Screen.MusicScreen.title)
            MusicScreen(musicViewModel = musicViewModel)
        }
        composable(
            route = Screen.DetailTherapistScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){backStackEntry->
            val id = backStackEntry.arguments?.getInt("id")
            mainViewModel.setCurrentScreen(Screen.DetailTherapistScreen)
            mainViewModel.setTitle(Screen.DetailTherapistScreen.title)
            if (id != null) {
                DetailTherapistScreen(detailTherapistViewModel = detailTherapistViewModel, id = id)
            }
        }
        composable(
            route = Screen.DetailArticleScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){backStackEntry->
            val id = backStackEntry.arguments?.getInt("id")
            mainViewModel.setCurrentScreen(Screen.DetailArticleScreen)
            mainViewModel.setTitle(Screen.DetailArticleScreen.title)
            if (id != null) {
                DetailArticleScreen(detailArticleViewModel = detailArticleViewModel, id = id)
            }
        }
        composable(
            route = Screen.DetailStoryScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ){backStackEntry->
            val id = backStackEntry.arguments?.getInt("id")
            mainViewModel.setCurrentScreen(Screen.DetailStoryScreen)
            mainViewModel.setTitle(Screen.DetailStoryScreen.title)
            if (id != null) {
                DetailStoryScreen(detailStoryViewModel = detailStoryViewModel, id = id)
            }
        }
        composable(Screen.BadMoodScreen.route){
            badMoodViewModel.onSelectedOptionChange(null)
            mainViewModel.setCurrentScreen(Screen.BadMoodScreen)
            mainViewModel.setTitle(Screen.BadMoodScreen.title)
            BadMoodScreen(
                badMoodViewModel = badMoodViewModel,
                navigateToArticleScreen = {category->
                    navController.navigate(Screen.ArticleScreen.createRoute(category = category))
                },
                navigateToStoryScreen = {category->
                    navController.navigate(Screen.StoryScreen.createRoute(category = category))
                },
                navigateToTherapistScreen = {category->
                    navController.navigate(Screen.BottomScreen.Therapist.createRoute(category = category))
                },
                navigateToVideoScreen = {category->
                    navController.navigate(Screen.VideoScreen.createRoute(category = category))
                },
                navigateToHomeScreen = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.GoodMoodScreen.route) {
            mainViewModel.setCurrentScreen(Screen.GoodMoodScreen)
            mainViewModel.setTitle(Screen.GoodMoodScreen.title)
            GoodMoodScreen(
                navigateToHomeScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    apiClient: ApiClient,
    userPreferences: UserPreferences,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel
){
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { BottomBar(mainViewModel = mainViewModel, controller, currentRoute) },
        topBar = { TopBar(mainViewModel = mainViewModel, controller = controller)}
    ) {
        MainNavigation(navController = controller, mainViewModel = mainViewModel, pd = it, apiClient = apiClient, userPreferences = userPreferences, loginViewModel = loginViewModel, signUpViewModel = signUpViewModel)
    }
}