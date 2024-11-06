package com.my.version.feature.evaluate.select.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio

data class EvaluationSelectUiState(
    val loadState: UiState<List<CoverAudio>> = UiState.Loading,
    val selected: Int = -1,
    val selectedCover: CoverAudio? = null,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)