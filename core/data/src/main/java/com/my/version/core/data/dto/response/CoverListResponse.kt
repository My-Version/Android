package com.my.version.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverListResponse(
    @SerialName("Music")
    val music: String,
    @SerialName("Singer")
    val artist: String
)
