package com.my.version.feature.cover.select

import androidx.lifecycle.ViewModel
import com.my.version.feature.cover.select.state.CoverFirstUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CoverFirstViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(CoverFirstUiState())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedIndex(selected: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selected = selected
            )
        }
    }
}