package com.my.version.feature.cover.main.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudioFile

data class CoverUiState(
    val loadState: UiState<List<CoverAudioFile>> = UiState.Loading,
    val currentAudio: CoverAudioFile? = null,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)
