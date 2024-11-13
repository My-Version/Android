package com.my.version.feature.evaluate.result.state

data class EvaluationResultAudioState(
    val url: String = "",
    val progress: Float = 0f,
    val isPlaying: Boolean = false,
    val isExpanded: Boolean = false,
    val prepared: Boolean = false,
)
