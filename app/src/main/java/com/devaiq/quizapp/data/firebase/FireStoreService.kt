package com.devaiq.quizapp.data.firebase

import com.devaiq.quizapp.data.model.SubjectModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FireStoreService {
    private val db = Firebase.firestore

    suspend fun fetchSubjects(): List<SubjectModel> {
        return try {
            val snapshot = db.collection("subjects").get().await()
            snapshot.documents.mapNotNull {
                it.toObject(SubjectModel::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
