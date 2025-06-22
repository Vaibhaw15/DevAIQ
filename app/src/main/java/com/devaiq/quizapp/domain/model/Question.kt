package com.devaiq.quizapp.domain.model

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String,
    val id: String = ""
)
