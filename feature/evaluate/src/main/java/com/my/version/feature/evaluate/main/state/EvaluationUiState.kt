package com.my.version.feature.evaluate.main.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.EvaluationDetail

data class EvaluationUiState(
    val loadState: UiState<List<EvaluationDetail>> = UiState.Loading,
    val sortByIndex: Int = 0,
    val isSortSheetVisible: Boolean = false
)
