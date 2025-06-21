package com.devaiq.quizapp.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.devaiq.quizapp.domain.repository.AuthRepository
import com.devaiq.quizapp.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val email = profileRepository.getCurrentUser()?.email ?: ""

    var name by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var currentPassword by mutableStateOf("")
    var message by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    init {
        loadUserName()
    }

    private fun loadUserName() {
        isLoading = true
        profileRepository.getUserNameFromFirestore { fetchedName, error ->
            isLoading = false
            if (fetchedName != null) {
                name = fetchedName.toString()
            } else {
                message = "Error loading name: $error"
            }
        }
    }

    fun updateProfile() {
        if (name.isBlank()) {
            message = "Name cannot be empty"
            return
        }

        isLoading = true
        profileRepository.updateUserName(name) { nameSuccess, nameError ->
            if (!nameSuccess) {
                isLoading = false
                message = "Failed to update name: $nameError"
                return@updateUserName
            }

            // If password field is filled
            if (newPassword.isNotBlank() && currentPassword.isNotBlank()) {
                if (newPassword.length < 6) {
                    isLoading = false
                    message = "Password must be at least 6 characters"
                    return@updateUserName
                }

                profileRepository.updatePasswordWithReAuth(
                    currentPassword,
                    newPassword
                ) { passSuccess, passError ->
                    isLoading = false
                    message = if (passSuccess) "Profile updated successfully"
                    else "Password update failed: $passError"
                }

            } else {
                isLoading = false
                message = "Name updated successfully"
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        profileRepository.logout()
        onLogout()
    }
}

