package com.my.version.core.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("id")
    val email: String,
    @SerialName("pw")
    val password: String
)