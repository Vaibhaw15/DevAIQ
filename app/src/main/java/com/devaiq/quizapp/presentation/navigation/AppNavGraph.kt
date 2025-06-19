package com.devaiq.quizapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devaiq.quizapp.presentation.auth.ForgetPasswordScreen
import com.devaiq.quizapp.presentation.auth.LogInScreen
import com.devaiq.quizapp.presentation.auth.RegisterScreen
import com.devaiq.quizapp.presentation.home.HomeScreen
import com.devaiq.quizapp.utils.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("login") { LogInScreen(navController) }
        composable("forget") { ForgetPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}
