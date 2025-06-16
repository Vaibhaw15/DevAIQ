package com.devaiq.quizapp.data.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
