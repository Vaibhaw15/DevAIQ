package com.devaiq.quizapp.presentation.navigation


import ProfileScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devaiq.quizapp.presentation.home.HomeScreen
import com.devaiq.quizapp.presentation.level.DifficultyScreen


fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
    composable(Screen.Progress.route) {
       // ProgressScreen()
    }
    composable(Screen.Profile.route) {
        ProfileScreen(navController)
    }


    composable(
        route = Screen.Difficulty.route + "/{subjectId}",
        arguments = listOf(navArgument("subjectId") {
            type = NavType.StringType
        })
    ) { entry ->
        val subjectId = entry.arguments?.getString("subjectId") ?: ""
        DifficultyScreen(navController, subjectId)
    }
}
