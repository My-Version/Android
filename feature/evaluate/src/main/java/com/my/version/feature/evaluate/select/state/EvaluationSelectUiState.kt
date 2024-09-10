package com.my.version.feature.evaluate.select.state

import com.my.version.core.domain.entity.CoverAudioFile

data class EvaluationSelectUiState(
    val selected: Int = -1,
    val musicList: List<CoverAudioFile> = emptyList()
)
