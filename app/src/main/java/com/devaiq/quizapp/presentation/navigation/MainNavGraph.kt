package com.devaiq.quizapp.presentation.navigation


import ProfileScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devaiq.quizapp.presentation.home.HomeScreen
import com.devaiq.quizapp.presentation.level.DifficultyScreen
import com.devaiq.quizapp.presentation.quiz.QuizScreen
import com.devaiq.quizapp.presentation.result.ResultScreen


fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    rootNavController: NavHostController
) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
    composable(Screen.Progress.route) {
       // ProgressScreen()
    }
    composable(Screen.Profile.route) {
        ProfileScreen(
            navController = navController,
            rootNavController = rootNavController)
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

    composable(
        route = Screen.Quiz.route + "/{subjectId}/{difficulty}",
        arguments = listOf(
            navArgument("subjectId") {
                type = NavType.StringType
            },
            navArgument("difficulty") {
                type = NavType.StringType
            }
        )
    ) { entry ->
        val subjectId = entry.arguments?.getString("subjectId") ?: ""
        val difficulty = entry.arguments?.getString("difficulty") ?: ""
         QuizScreen(subjectId, difficulty, navController)
    }

    composable(
        "result/{correct}/{total}",
        arguments = listOf(
            navArgument("correct") { type = NavType.IntType },
            navArgument("total") { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val correct = backStackEntry.arguments?.getInt("correct") ?: 0
        val total = backStackEntry.arguments?.getInt("total") ?: 0
        ResultScreen(
            correct = correct,
            total = total,
            onExit = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            }
        )
    }

}
