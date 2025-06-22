package com.devaiq.quizapp.data.model

data class QuestionModel(
    val questionText : String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
    val explanation: String = "",
    val id: String = "",
)
