package com.my.version.core.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class EvaluationResult(
    val title: String,
    val date: String,
    val similarity: Int,
    val leastSimilarPeriod: Double,
    val mostSimilarPeriod: Double,
    val timeLength: Int,
    val imageUrl: String
)
