package com.my.version.feature.evaluate.record.state

data class EvaluationRecordUiState(
    val musicUri: String = "",
    val songLyrics: Map<Long, String> = mapOf(),
    val currentTimeStamp: Long = 0L,
    val isRecordEnabled: Boolean = false,
    val isNextEnabled: Boolean = false,
    val isPlaying: Boolean = false
)