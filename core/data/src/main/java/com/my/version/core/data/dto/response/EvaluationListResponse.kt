package com.my.version.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvaluationListResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("userId")
    val userId: String,
    @SerialName("recordLocation")
    val recordUrl: String,
    @SerialName("coverLocation")
    val coverUrl: String,
    @SerialName("createTime")
    val createdTime: String?,
    @SerialName("similarity")
    val similarityScore: String?,
    @SerialName("worst_time")
    val leastSimilarPeriod: String?,
    @SerialName("best_time")
    val mostSimilarPeriod: String?,
    @SerialName("time_length")
    val similarityTimeLength: String?,
    @SerialName("imgLocation")
    val imageUrl: String?
)
