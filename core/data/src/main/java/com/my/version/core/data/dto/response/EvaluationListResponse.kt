package com.my.version.core.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvaluationListResponse(
    @SerialName("id")
    val title: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("similarity")
    val similarityScore: Double,
    @SerialName("worst_time")
    val leastSimilarPeriod: Double,
    @SerialName("best_time")
    val mostSimilarPeriod: Double,
    @SerialName("time_length")
    val similarityTimeLength: Int,
    @SerialName("imgLocation")
    val imageUrl: String
)
