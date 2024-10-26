package com.my.version.core.data.service

import com.my.version.core.data.dto.request.SignInRequest
import com.my.version.core.data.dto.request.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun signIn(@Body request: SignInRequest): String

    @POST("register")
    suspend fun signUp(@Body request: SignUpRequest): String
}