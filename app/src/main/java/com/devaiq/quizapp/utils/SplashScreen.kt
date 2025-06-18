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


@Composable
fun SplashScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState(initial = false)

    LaunchedEffect(key1 = isUserLoggedIn) {
        delay(2000)
        if (isUserLoggedIn) {
            navController.navigate("landing") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
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