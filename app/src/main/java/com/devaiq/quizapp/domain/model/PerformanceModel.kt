package com.devaiq.quizapp.domain.model

import java.util.Date

data class PerformanceModel(
    val subjectId: String = "",
    val difficulty: String = "",
    val correct: Int = 0,
    val total: Int = 0,
    val timestamp: Date = Date()
)

