package com.my.version.feature.evaluate.result.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.EvaluationResult

data class EvaluationResultUiState(
    val loadState: UiState<EvaluationResult> = UiState.Loading,
    val progressCover: Float = 0f,
    val progressRecord: Float = 0f,
    val isCoverEnabled: Boolean = false,
    val isRecordEnabled: Boolean = false
)