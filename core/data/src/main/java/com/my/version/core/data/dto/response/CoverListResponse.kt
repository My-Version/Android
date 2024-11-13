package com.my.version.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoverListResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("artist")
    val artist: String,
    @SerialName("songTitle")
    val music: String,
    @SerialName("s3FileLocation")
    val s3FileLocation: String?,
    @SerialName("createdDate")
    val createdDate: String
)
