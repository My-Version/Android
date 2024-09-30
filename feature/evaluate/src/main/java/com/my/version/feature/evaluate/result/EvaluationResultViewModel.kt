package com.my.version.feature.evaluate.result

import androidx.lifecycle.ViewModel
import com.my.version.feature.evaluate.result.state.EvaluationResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EvaluationResultViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationResultUiState())
    val uiState = _uiState.asStateFlow()

    fun coverClicked() = _uiState.update { currentState ->
        currentState.copy(
            isCoverEnabled = !_uiState.value.isCoverEnabled,
            isRecordEnabled = if (_uiState.value.isRecordEnabled) false else _uiState.value.isRecordEnabled
        )
    }

    fun recordClicked() = _uiState.update { currentState ->
        currentState.copy(
            isRecordEnabled = !_uiState.value.isRecordEnabled,
            isCoverEnabled = if (_uiState.value.isCoverEnabled) false else _uiState.value.isCoverEnabled
        )
    }
}