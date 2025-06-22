package com.devaiq.quizapp.presentation.quiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.model.Question
import com.devaiq.quizapp.domain.repository.QuestionRepository
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

     fun saveResultToFirebase(userId: String, subjectId: String, difficulty: String){
         viewModelScope.launch {
             repository.saveQuizResult(
                 userId = userId,
                 subjectId = subjectId,
                 difficulty = difficulty,
                 correctCount = correctCount,
                 totalQuestions = questions.size
             )
         }
    }

}
