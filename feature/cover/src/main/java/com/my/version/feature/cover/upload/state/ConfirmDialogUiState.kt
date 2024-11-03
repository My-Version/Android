package com.my.version.feature.cover.upload.state

import com.my.version.core.common.state.UiState

data class ConfirmDialogUiState(
    val loadState: UiState<String> = UiState.Empty,
    val dialogVisibility: Boolean = false
)