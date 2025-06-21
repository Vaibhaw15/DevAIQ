package com.devaiq.quizapp.presentation.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devaiq.quizapp.presentation.home.HomeScreen


fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
    composable(Screen.Progress.route) {
       // ProgressScreen()
    }
    composable(Screen.Profile.route) {
        //ProfileScreen()
    }
}
