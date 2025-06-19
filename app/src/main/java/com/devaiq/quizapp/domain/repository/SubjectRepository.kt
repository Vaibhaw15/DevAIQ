package com.devaiq.quizapp.domain.repository

import com.devaiq.quizapp.domain.model.Subject

interface SubjectRepository {
    suspend fun getSubjects():List<Subject>
}