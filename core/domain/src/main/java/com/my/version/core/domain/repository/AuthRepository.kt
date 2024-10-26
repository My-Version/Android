package com.my.version.core.domain.repository

interface AuthRepository {
    suspend fun postSignIn(email: String, password: String): Result<String>
    suspend fun postSignUp(email: String, password: String): Result<String>
}