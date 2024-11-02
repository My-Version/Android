package com.my.version.feature.cover.select.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudio

data class CoverSelectUiState(
    val loadState: UiState<List<MusicAudio>> = UiState.Loading,
    val selected: Int = -1,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false,
    val currentMusic: MusicAudio? = null
)