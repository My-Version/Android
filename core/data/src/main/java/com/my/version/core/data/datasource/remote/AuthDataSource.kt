package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.request.SignInRequest
import com.my.version.core.data.dto.request.SignUpRequest

interface AuthDataSource {
    suspend fun postSignIn(request: SignInRequest): String
    suspend fun postSignUp(request: SignUpRequest): String
}