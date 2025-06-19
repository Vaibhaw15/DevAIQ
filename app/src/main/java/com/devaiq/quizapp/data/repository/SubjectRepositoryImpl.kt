package com.devaiq.quizapp.data.repository

import com.devaiq.quizapp.data.firebase.FireStoreService
import com.devaiq.quizapp.domain.model.Subject
import com.devaiq.quizapp.domain.repository.SubjectRepository

class SubjectRepositoryImpl(
    private val fireStoreService: FireStoreService
): SubjectRepository {


    override suspend fun getSubjects(): List<Subject> {
        return fireStoreService.fetchSubjects().map{
            Subject(
                it.id,it.name,it.iconUrl
            )
        }
    }

}