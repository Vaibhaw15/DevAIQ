package com.devaiq.quizapp.presentation.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(
    correct: Int,
    total: Int,
    onExit: () -> Unit,
) {
    val percentage = (correct.toFloat() / total) * 100


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎉 Quiz Completed!", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("Correct Answers: $correct", fontSize = 20.sp, color = Color.Green)
        Text("Wrong Answers: ${total - correct}", fontSize = 20.sp, color = Color.Red)
        Text("Score: ${"%.2f".format(percentage)}%", fontSize = 20.sp)
        Spacer(Modifier.height(32.dp))


        Spacer(Modifier.height(16.dp))

        Button(onClick = onExit, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Home")
        }
    }
}
