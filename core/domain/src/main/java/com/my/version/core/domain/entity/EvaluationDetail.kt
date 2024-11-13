package com.my.version.core.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class EvaluationDetail(
    val title: String,
    val date: String,
    val similarity: Int?,
    val leastSimilarPeriod: Double?,
    val mostSimilarPeriod: Double?,
    val timeLength: Int?,
    val imageUrl: String?,
    val coverUrl: String,
    val recordUrl: String
)
