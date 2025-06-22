package com.devaiq.quizapp.presentation.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun QuizScreen(
    subjectId: String,
    difficulty: String,
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadQuestions(subjectId, difficulty)
    }

    val question = viewModel.questions.getOrNull(viewModel.currentIndex)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Question ${viewModel.currentIndex + 1}/${viewModel.questions.size}")

        LinearProgressIndicator(
            progress = (viewModel.currentIndex + 1).toFloat() / viewModel.questions.size,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        question?.let {
            Text(
                text = it.questionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            it.options.forEach { option ->
                OutlinedButton(
                    onClick = {
                        if (!viewModel.isAnswerSubmitted) {
                            viewModel.selectedOption = option
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        1.dp,
                        if (viewModel.selectedOption == option) Color.White else Color.Gray
                    )
                ) {
                    Text(option)
                }
                Spacer(Modifier.height(8.dp))
            }

            // Show Submit Button (only before answer is submitted)
            if (!viewModel.isAnswerSubmitted) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.submitAnswer() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.selectedOption != null
                ) {
                    Text("Submit")
                }
            }

            // Show Explanation after Submit
            if (viewModel.showExplanation) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Explanation: ${it.explanation}",
                    fontStyle = FontStyle.Italic,
                    color = Color.Green
                )
            }

            // Show Next Button only after submitting
            if (viewModel.isAnswerSubmitted) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.nextQuestionOrFinish(
                            userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user",
                            subjectId = subjectId,
                            difficulty = difficulty
                        )
                        if(viewModel.navigateToResultScreen){
                            navController.navigate("result/${viewModel.correctCount}/${viewModel.questions.size}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (viewModel.currentIndex == viewModel.questions.lastIndex) "Finish" else "Next")
                }
            }
        } ?: Text("Loading...")
    }
}

