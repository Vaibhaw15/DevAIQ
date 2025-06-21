package com.devaiq.quizapp.data.repository

import com.devaiq.quizapp.domain.repository.ProfileRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override fun getCurrentUser() = auth.currentUser

    override fun getUserNameFromFirestore(onResult: (String?, String?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return onResult(null, "User not logged in")

        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val name = doc.getString("name")
                onResult(name, null)
            }
            .addOnFailureListener { e ->
                onResult(null, e.message)
            }
    }

    override fun updateUserName(newName: String, onResult: (Boolean, String?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return onResult(false, "User not logged in")
        firestore.collection("users").document(uid)
            .update("name", newName)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.message) }
    }

    // ✅ New: Secure password update with re-authentication
    override fun updatePasswordWithReAuth(
        currentPassword: String,
        newPassword: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = auth.currentUser
        val email = user?.email

        if (user == null || email == null) {
            onResult(false, "User not logged in")
            return
        }

        val credential = EmailAuthProvider.getCredential(email, currentPassword)

        user.reauthenticate(credential)
            .addOnSuccessListener {
                user.updatePassword(newPassword)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener { e ->
                        onResult(false, "Password update failed: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                onResult(false, "Re-authentication failed: ${e.message}")
            }
    }
}
