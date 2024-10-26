package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.AuthDataSource
import com.my.version.core.data.dto.request.SignInRequest
import com.my.version.core.data.dto.request.SignUpRequest
import com.my.version.core.data.service.AuthService
import timber.log.Timber
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {

    override suspend fun postSignIn(request: SignInRequest): String {
        val response = authService.signIn(request)

        Timber.tag("SignIn")
            .d("in datasource => ${request.email}, ${request.password} and response => $response")
        return response
    }

    override suspend fun postSignUp(request: SignUpRequest): String = authService.signUp(request)

}