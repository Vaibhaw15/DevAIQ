package com.devaiq.quizapp.data.repository

import com.devaiq.quizapp.data.firebase.FireStoreService
import com.devaiq.quizapp.domain.model.Question
import com.devaiq.quizapp.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val fireStoreService: FireStoreService
): QuestionRepository {

    override suspend fun getQuestions(
        subjectId: String,
        difficulty: String
    ): List<Question> {
        return fireStoreService.fetchQuestions(subjectId, difficulty)
            .getOrElse { emptyList() }
            .map { questionModel ->
                Question(
                    questionText = questionModel.questionText,
                    options = questionModel.options,
                    correctAnswer = questionModel.correctAnswer,
                    explanation = questionModel.explanation
                )
            }
    }

    override suspend fun saveQuizResult(
        userId: String,
        subjectId: String,
        difficulty: String,
        correctCount: Int,
        totalQuestions: Int,
    ): Result<Unit> {
        return fireStoreService.saveQuizResult(
            userId = userId,
            subjectId = subjectId,
            difficulty = difficulty,
            correctCount = correctCount,
            totalQuestions = totalQuestions
        )
    }




}