package com.devaiq.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.devaiq.quizapp.presentation.auth.RegisterScreen
import com.devaiq.quizapp.ui.theme.DevAIQTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevAIQTheme {
                val navController = rememberNavController()
                Surface {
                    RegisterScreen(navController = navController)
                }
            }
        }
    }
}
