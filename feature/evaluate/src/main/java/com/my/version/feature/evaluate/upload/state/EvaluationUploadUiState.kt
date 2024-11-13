package com.my.version.feature.evaluate.upload.state

import com.my.version.core.common.state.UiState

data class EvaluationUploadUiState(
    val filePath: String = "",
    val fileLength: Int = 0,
    val songLyrics: Map<Long, String> = mapOf(),
    val currentTimeStamp: Long = 0L,
    val lyricIndex: Int = 0,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val dialogLoadState: UiState<String> = UiState.Empty,
    val uploadDialogVisibility: Boolean = false
)
