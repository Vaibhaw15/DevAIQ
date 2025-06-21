package com.devaiq.quizapp.domain.repository

import com.google.firebase.auth.FirebaseUser

interface ProfileRepository {
    fun getCurrentUser(): FirebaseUser?

    fun getUserNameFromFirestore(onResult: (String?, String?) -> Unit)

    fun updateUserName(newName: String, onResult: (Boolean, String?) -> Unit)


    fun updatePasswordWithReAuth(
        currentPassword: String,
        newPassword: String,
        onResult: (Boolean, String?) -> Unit
    )
}
