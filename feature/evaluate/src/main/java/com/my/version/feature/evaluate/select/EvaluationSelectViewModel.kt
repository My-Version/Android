package com.my.version.feature.evaluate.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.evaluate.select.state.EvaluationSelectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationSelectViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationSelectUiState())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedIndex(selectedIndex: Int) = viewModelScope.launch {
        _uiState.update {
            _uiState.value.copy(
                selected = selectedIndex
            )
        }
    }

}