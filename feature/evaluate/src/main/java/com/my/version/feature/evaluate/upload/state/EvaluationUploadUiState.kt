package com.my.version.feature.evaluate.upload.state

import com.my.version.core.domain.entity.MusicAudioFile

data class EvaluationUploadUiState(
    val filePath: String = "",
    val fileLength: Int = 0,
    val music: MusicAudioFile? = null,
    val songLyrics: Map<Long, String> = mapOf(),
    val currentTimeStamp: Long = 0L,
    val lyricIndex: Int = 0,
    val isPlaying: Boolean = false,
    val progress: Float = 0f
)
