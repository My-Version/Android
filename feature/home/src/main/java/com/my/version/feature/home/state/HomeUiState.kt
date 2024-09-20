package com.my.version.feature.home.state

import androidx.compose.ui.graphics.painter.BrushPainter
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudioFile

data class HomeUiState(
    val loadState: UiState<List<MusicAudioFile>> = UiState.Loading,
    val currentMusic: MusicAudioFile? = null,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)

val tempList1 = listOf<MusicAudioFile>(
    MusicAudioFile(
        title = "바보",
        artist = "빅뱅",
        audio = null
    ),
    MusicAudioFile(
        title = "Wonderful",
        artist = "빅뱅",
        audio = null
    ),
    MusicAudioFile(
        title = "Tonight",
        artist = "빅뱅",
        audio = null
    ),
    MusicAudioFile(
        title = "Ditto",
        artist = "NewJeans",
        audio = null
    )
)