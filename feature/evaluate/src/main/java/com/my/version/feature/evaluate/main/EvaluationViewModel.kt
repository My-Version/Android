package com.my.version.feature.evaluate.main

import androidx.lifecycle.ViewModel
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.EvaluationResult
import com.my.version.feature.evaluate.main.state.EvaluationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EvaluationViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getEvaluationList()
    }

    fun getEvaluationList() {
        _uiState.update { currentState ->
            currentState.copy(
                loadState = UiState.Success(
                    listOf(
                        EvaluationResult(
                            title = "title",
                            date = "date"
                        )
                    )
                )
            )
        }
    }

    fun updateSortByIndex(index: Int) = _uiState.update { currentState ->
        currentState.copy(
            sortByIndex = index
        )
    }

    fun updateSheetVisibility(isVisible: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isSortSheetVisible = isVisible
        )
    }
}