package com.my.version.feature.cover.select.state

import com.my.version.core.domain.entity.MusicAudioFile

data class CoverSelectUiState(
    val selected: Int = -1,
    val musicList: List<MusicAudioFile> = emptyList()
)