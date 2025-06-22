package com.devaiq.quizapp.domain.repository

import com.devaiq.quizapp.domain.model.Question

interface QuestionRepository {
    suspend fun getQuestions(
        subjectId: String,
        difficulty: String,
    ): List<Question>

}