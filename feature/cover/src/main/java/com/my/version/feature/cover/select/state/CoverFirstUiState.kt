package com.my.version.feature.cover.select.state

import com.my.version.core.designsystem.type.TempItem

data class CoverFirstUiState(
    val selected: Int = -1,
    val musicList: List<TempItem> = emptyList()
)