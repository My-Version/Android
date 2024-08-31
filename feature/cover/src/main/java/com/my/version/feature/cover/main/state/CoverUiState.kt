package com.my.version.feature.cover.main.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudioFile

data class CoverUiState (
    val loadState: UiState<List<CoverAudioFile>> = UiState.Loading
)
