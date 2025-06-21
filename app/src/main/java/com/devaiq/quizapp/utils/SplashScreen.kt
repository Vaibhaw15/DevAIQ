package com.devaiq.quizapp.utils

import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.devaiq.quizapp.presentation.auth.LoginViewModel
import com.devaiq.quizapp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SplashScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        delay(1000)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Quiz App", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}