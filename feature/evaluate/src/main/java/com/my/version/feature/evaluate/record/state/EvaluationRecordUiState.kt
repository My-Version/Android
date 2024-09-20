package com.my.version.feature.evaluate.record.state

import com.my.version.core.domain.entity.MusicAudioFile

data class EvaluationRecordUiState(
    val music: MusicAudioFile?= null,
    val songLyrics: Map<Long, String> = mapOf(),
    val currentTimeStamp: Long = 0L,
    val isNextEnabled: Boolean = true,
)