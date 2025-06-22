package com.devaiq.quizapp.data.firebase

import android.util.Log
import com.devaiq.quizapp.data.model.DifficultyModel
import com.devaiq.quizapp.data.model.QuestionModel
import com.devaiq.quizapp.data.model.SubjectModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.Result

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


    suspend fun fetchSubjectsLevels(subjectId: String): List<DifficultyModel> {
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

    suspend fun fetchQuestions(subjectId: String, difficulty: String): Result<List<QuestionModel>> {
        return try {
            Log.d("FETCH", "subjectId=$subjectId difficulty=$difficulty") // check values

            val snapshot = db.collection("subjects")
                .document(subjectId)
                .collection("levels")
                .document(difficulty.lowercase(Locale.getDefault()))
                .collection("questions")
                .get()
                .await()

            Log.d("FETCH", "Documents fetched: ${snapshot.size()}")

            for (doc in snapshot.documents) {
                Log.d("FETCH_DOC", doc.data.toString())
            }

            val questions = snapshot.documents.mapNotNull { doc ->
                val question = doc.toObject(QuestionModel::class.java)
                question?.copy(id = doc.id)
            }

            Result.success(questions)
        } catch (e: Exception) {
            Log.e("FETCH", "Exception: ${e.localizedMessage}")
            Result.failure(e)
        }
    }


    fun saveQuizResult(
        userId: String, subjectId: String, difficulty: String,
        correctCount: Int, totalQuestions: Int,
    ): Result<Unit> {
       return try {
            val result = hashMapOf(
                "correct" to correctCount,
                "total" to totalQuestions,
                "timestamp" to FieldValue.serverTimestamp()
            )

            Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("results")
                .document(subjectId)
                .collection(difficulty)
                .document("latest")
                .set(result)
                .addOnSuccessListener {
                    Log.d("Firestore", "Result saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error saving result", e)
                }

            Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("results")
                .document(subjectId)
                .set(mapOf("active" to true), SetOptions.merge())

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("Firestore", "Error saving result", e)
            Result . failure (e)

        }

    }

}