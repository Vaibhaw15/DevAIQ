package com.devaiq.quizapp.presentation.auth


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
        viewModelScope.launch {
            val result = try {
                authRepository.forgetPassword(trimmedEmail)
            } catch (e: Exception) {
                isLoading = false
                onFailure(e.message ?: "Unknown error")
                return@launch
            }
            print("vaibhaw")
            print(result)
            isLoading = false
            result.fold(
                onSuccess = { onSuccess() },
                onFailure = { onFailure(it.message ?: "Failed to send reset link") }
            )
        }
    }
}
