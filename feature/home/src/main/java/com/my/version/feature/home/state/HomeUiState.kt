package com.my.version.feature.home.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudio

data class HomeUiState(
    val loadState: UiState<List<MusicAudio>> = UiState.Loading,
    val currentMusic: MusicAudio? = null,
    val isMusicPlaying: Boolean = false,
    val audioProgress: Float = 0f,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)