package com.devaiq.quizapp.domain.repository

import com.devaiq.quizapp.domain.model.Difficulty

interface DifficultyRepository {
    suspend fun getDifficulties(subjectId:String):List<Difficulty>
}