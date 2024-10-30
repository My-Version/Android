package com.my.version.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverListResponse(
    @SerialName("music")
    val music: String,
    @SerialName("singer")
    val artist: String
)
