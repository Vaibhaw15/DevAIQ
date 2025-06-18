package com.devaiq.quizapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loginError by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        checkUser()
    }

    private fun checkUser() {
        _isUserLoggedIn.value = auth.currentUser != null
    }

    fun login(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onFailure("Email or Password cannot be empty")
            return
        }

        isLoading = true
        loginError = null

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    _isUserLoggedIn.value = true
                    onSuccess()
                } else {
                    loginError = task.exception?.message
                    onFailure(task.exception?.message ?: "Login failed")
                }
            }
    }
}