
package com.devaiq.quizapp.data.firebase

import com.devaiq.quizapp.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService(private val auth: FirebaseAuth) {

    suspend fun registerUser(name: String, email: String, password: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                userProfileChangeRequest {
                    displayName = name
                }
            )?.await()

            result.user?.uid?.let { uid ->
                val user = UserModel(
                    uid = uid,
                    name = name,
                    email = email
                )
                Firebase.firestore.collection("users")
                    .document(uid)
                    .set(user)
                    .await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
