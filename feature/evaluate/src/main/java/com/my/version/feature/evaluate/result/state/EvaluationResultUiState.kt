package com.my.version.feature.evaluate.result.state

data class EvaluationResultUiState(
    val progressCover: Float = 0f,
    val progressRecord: Float = 0f,
    val isCoverEnabled: Boolean = false,
    val isRecordEnabled: Boolean = false,

    val title: String = "",
    val createdDate: String = "",
    val similarity: Int = 0,
    val mostSimilarPeriod: Double = 0.0,
    val leastSimilarPeriod: Double = 0.0,
    val timeLength: Int = 0,
    val coverUrl: String = "",
    val recordUrl: String = "",
    val imageUrl: String = ""
)