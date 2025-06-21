package com.devaiq.quizapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devaiq.quizapp.presentation.auth.ForgetPasswordScreen
import com.devaiq.quizapp.presentation.auth.LogInScreen
import com.devaiq.quizapp.presentation.auth.RegisterScreen

// AuthNavGraph.kt
fun NavGraphBuilder.authGraph(navController: NavHostController) {
    composable(Screen.Login.route) {
        LogInScreen(navController)
    }
    composable(Screen.Register.route) {
        RegisterScreen(navController)
    }
    composable(Screen.Forget.route) {
        ForgetPasswordScreen(navController)
    }
}
