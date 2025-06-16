package com.devaiq.quizapp.presentation.auth

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun registerUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            loading = true
            errorMessage = null
            val result = authRepository.register(name.trim(), email.trim(), password.trim())
            loading = false
            result.onSuccess { onSuccess() }
            result.onFailure { errorMessage = it.localizedMessage }
        }
    }
}
