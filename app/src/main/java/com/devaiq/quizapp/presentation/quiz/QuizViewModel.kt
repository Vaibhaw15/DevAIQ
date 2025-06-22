package com.devaiq.quizapp.presentation.quiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.model.Question
import com.devaiq.quizapp.domain.repository.QuestionRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    var questions by mutableStateOf<List<Question>>(emptyList())
    var currentIndex by mutableStateOf(0)
    var selectedOption by mutableStateOf<String?>(null)
    var isAnswerSubmitted by mutableStateOf(false)
    var showExplanation by mutableStateOf(false)
    var correctCount by mutableStateOf(0)
    var navigateToResultScreen by mutableStateOf(false)


    fun loadQuestions(subjectId: String, difficulty: String) {
        viewModelScope.launch {
            questions = repository.getQuestions(subjectId, difficulty)
        }
    }

    fun submitAnswer() {
        val currentQuestion = questions.getOrNull(currentIndex)
        if (selectedOption == currentQuestion?.correctAnswer) {
            correctCount++
        }
        isAnswerSubmitted = true
        showExplanation = true
    }

    fun nextQuestionOrFinish(userId: String, subjectId: String, difficulty: String) {
        if (currentIndex < questions.lastIndex) {
            currentIndex++
            selectedOption = null
            isAnswerSubmitted = false
            showExplanation = false
        } else {
            saveResultToFirebase(userId, subjectId, difficulty)
            navigateToResultScreen = true
        }
    }


    fun saveResultToFirebase(userId: String, subjectId: String, difficulty: String) {
        val result = hashMapOf(
            "correct" to correctCount,
            "total" to questions.size,
            "timestamp" to FieldValue.serverTimestamp()
        )

        Firebase.firestore
            .collection("users")
            .document(userId)
            .collection("results")
            .document(subjectId)
            .collection(difficulty)
            .document("latest")
            .set(result)
            .addOnSuccessListener {
                Log.d("Firestore", "Result saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving result", e)
            }

             Firebase.firestore
                 .collection("users")
            .document(userId)
            .collection("results")
            .document(subjectId)
            .set(mapOf("active" to true), SetOptions.merge())
    }


}
