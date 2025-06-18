package com.devaiq.quizapp.presentation.auth


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var email by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun sendResetLink(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty()) {
            onFailure("Please enter your email")
            return
        }

        isLoading = true
        firebaseAuth.sendPasswordResetEmail(trimmedEmail)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Something went wrong")
                }
            }
    }
}

