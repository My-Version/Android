package com.my.version.feature.evaluate.result.state

data class EvaluationResultUiState(
    val title: String = "",
    val createdDate: String = "",
    val similarity: Int = 0,
    val mostSimilarPeriod: Double = 0.0,
    val leastSimilarPeriod: Double = 0.0,
    val timeLength: Int = 0,
    val imageUrl: String = ""
)