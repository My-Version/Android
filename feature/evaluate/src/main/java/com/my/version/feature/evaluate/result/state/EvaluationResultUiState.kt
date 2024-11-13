package com.my.version.feature.evaluate.result.state

import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.EvaluationDetail

data class EvaluationResultUiState(
    val loadState: UiState<EvaluationDetail> = UiState.Loading,
    val progressCover: Float = 0f,
    val progressRecord: Float = 0f,
    val isCoverEnabled: Boolean = false,
    val isRecordEnabled: Boolean = false
)