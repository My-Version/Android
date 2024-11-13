package com.my.version.core.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("id")
    val email: String,
    @SerialName("password")
    val password: String
)