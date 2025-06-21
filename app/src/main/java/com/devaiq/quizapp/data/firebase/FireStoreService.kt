package com.devaiq.quizapp.data.firebase

import com.devaiq.quizapp.data.model.DifficultyModel
import com.devaiq.quizapp.data.model.SubjectModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FireStoreService {
    private val db = Firebase.firestore

    suspend fun fetchSubjects(): List<SubjectModel> {
        return try {
            val snapshot = db.collection("subjects").get().await()
            snapshot.documents.mapNotNull { doc ->
                val subject = doc.toObject(SubjectModel::class.java)
                subject?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun fetchSubjectsLevels(subjectId:String):List<DifficultyModel>{
        return try {
            val snapshot = db.collection("subjects")
                .document(subjectId).collection("levels")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                val difficulty = doc.toObject(DifficultyModel::class.java)
                difficulty?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
