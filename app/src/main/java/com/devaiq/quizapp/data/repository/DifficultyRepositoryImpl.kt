package com.devaiq.quizapp.data.repository

import com.devaiq.quizapp.data.firebase.FireStoreService
import com.devaiq.quizapp.domain.model.Difficulty
import com.devaiq.quizapp.domain.repository.DifficultyRepository

class DifficultyRepositoryImpl(
    private val fireStoreService: FireStoreService
):DifficultyRepository{

    override suspend fun getDifficulties(subjectId:String): List<Difficulty> {
        return fireStoreService.fetchSubjectsLevels(subjectId).map{
            Difficulty(
                it.id,
                it.level
            )
        }
    }
}