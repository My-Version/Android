package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.AuthDataSource
import com.my.version.core.data.dto.request.SignInRequest
import com.my.version.core.data.dto.request.SignUpRequest
import com.my.version.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postSignIn(email: String, password: String): Result<String> = runCatching {
        val request = SignInRequest(email, password)
        authDataSource.postSignIn(request)
    }


    override suspend fun postSignUp(email: String, password: String): Result<String> = runCatching {
        val request = SignUpRequest(email, password)
        authDataSource.postSignUp(request)
    }
}