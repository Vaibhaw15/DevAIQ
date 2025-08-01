package com.devaiq.quizapp.data.repository
import com.devaiq.quizapp.data.firebase.AuthService
import com.devaiq.quizapp.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        return authService.registerUser(name, email, password)
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return  authService.login(email, password)
    }

    override suspend fun forgetPassword(email: String): Result<Unit> {
        return authService.sendResetLink(email)
    }
}