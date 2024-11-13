package com.my.version.feature.evaluate.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.EvaluationDetail
import com.my.version.core.domain.repository.EvaluationRepository
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.main.state.EvaluationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationViewModel @Inject constructor(
    private val evaluationRepository: EvaluationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EvaluationSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getEvaluationList() = viewModelScope.launch {
        evaluationRepository.getEvaluationList()
            .onSuccess { evaluationList ->
                val newState =
                    if (evaluationList.isEmpty()) UiState.Empty
                    else UiState.Success(evaluationList)

                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = newState
                    )
                }
            }
            .onFailure {
                it.printStackTrace()
                _sideEffect.emit(EvaluationSideEffect.ShowToast(R.string.evaluation_main_get_list_failed))
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UiState.Empty
                    )
                }
            }
    }

    fun onEvaluationResultSelected(result: EvaluationDetail) = viewModelScope.launch {
        _sideEffect.emit(EvaluationSideEffect.NavigateToResult(result))
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