package com.my.version.feature.evaluate.main

import androidx.lifecycle.ViewModel
import com.my.version.feature.evaluate.main.state.EvaluationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EvaluationViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(EvaluationUiState())
    val uiState = _uiState.asStateFlow()


}