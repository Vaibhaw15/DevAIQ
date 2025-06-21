package com.devaiq.quizapp.presentation.navigation


import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.compose.runtime.Composable
import com.devaiq.quizapp.presentation.home.MainScreen
import com.devaiq.quizapp.utils.SplashScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        // Auth Flow
        authGraph(navController)

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
