package com.devaiq.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.devaiq.quizapp.presentation.navigation.RootNavGraph
import com.devaiq.quizapp.ui.theme.DevAIQTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevAIQTheme { // 👈 No need to pass system theme
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    RootNavGraph(navController = navController)
                }
            }
        }
    }
}