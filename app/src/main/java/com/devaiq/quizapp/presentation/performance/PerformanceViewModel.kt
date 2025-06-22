package com.devaiq.quizapp.presentation.performance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.model.PerformanceModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PerformanceViewModel @Inject constructor() : ViewModel() {

    var performanceList by mutableStateOf<List<PerformanceModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)


    fun loadPerformance(userId: String) {
        viewModelScope.launch {
            isLoading = true
            val subjects = fetchSubjects(userId)
            val difficulties = listOf("Easy", "Medium", "Hard")

            val results = fetchLatestPerformance(userId, subjects, difficulties)
            performanceList = results
            isLoading = false
        }
    }
    suspend fun fetchSubjects(userId: String): List<String> {
        val firestore = Firebase.firestore

        val subjectSnapshots = firestore
            .collection("users")
            .document(userId)
            .collection("results")
            .get()
            .await()

        return subjectSnapshots.documents.map { it.id }
    }

    suspend fun fetchLatestPerformance(
        userId: String,
        subjects: List<String>,
        difficulties: List<String>
    ): List<PerformanceModel> {
        val firestore = Firebase.firestore
        val resultList = mutableListOf<PerformanceModel>()

        for (subjectId in subjects) {
            for (difficulty in difficulties) {
                val latestDoc = firestore
                    .collection("users")
                    .document(userId)
                    .collection("results")
                    .document(subjectId)
                    .collection(difficulty)
                    .document("latest")
                    .get()
                    .await()

                if (latestDoc.exists()) {
                    val correct = (latestDoc["correct"] as? Long)?.toInt() ?: 0
                    val total = (latestDoc["total"] as? Long)?.toInt() ?: 0
                    val timestamp = (latestDoc["timestamp"] as? Timestamp)?.toDate() ?: Date()

                    resultList.add(
                        PerformanceModel(
                            subjectId = subjectId,
                            difficulty = difficulty,
                            correct = correct,
                            total = total,
                            timestamp = timestamp
                        )
                    )
                }
            }
        }

        return resultList.sortedByDescending { it.timestamp }
    }




}
