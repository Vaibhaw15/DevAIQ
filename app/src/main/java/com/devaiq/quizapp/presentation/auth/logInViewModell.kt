package com.devaiq.quizapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
        // TODO: Replace with actual implementation if needed
        // For now, just set to false or use authRepository if it provides user state
        _isUserLoggedIn.value = false
    }

    fun login(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onFailure("Email or Password cannot be empty")
            return
        }

        isLoading = true
        loginError = null

        viewModelScope.launch {
            val result = try {
                authRepository.login(email, password)
            } catch (e: Exception) {
                isLoading = false
                onFailure(e.message ?: "Unknown error")
                return@launch
            }
            isLoading = false
            result.fold(
                onSuccess = { onSuccess() },
                onFailure = { onFailure(it.message ?: "Login failed") }
            )
        }
    }
}