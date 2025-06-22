package com.devaiq.quizapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String = "", val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Forget : Screen("forget")
    object Main : Screen("main")
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Performance : Screen("performance", "Performance", Icons.Default.Place)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Difficulty : Screen("difficulty_screen")
    object Quiz : Screen("quiz")
    object Result : Screen("result")
}
