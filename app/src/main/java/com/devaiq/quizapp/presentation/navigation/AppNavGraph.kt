package com.devaiq.quizapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "subject") {
        composable("subject") {
            // TODO: Call SubjectScreen() here
        }

        composable("level") {
            // TODO: Call LevelScreen() here
        }

        // Add more screens/routes here as needed
    }
}