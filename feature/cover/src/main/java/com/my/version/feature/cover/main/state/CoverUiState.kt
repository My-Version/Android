package com.my.version.feature.cover.main.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio

data class CoverUiState(
    val loadState: UiState<List<CoverAudio>> = UiState.Loading,
    val currentAudio: CoverAudio? = null,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)
