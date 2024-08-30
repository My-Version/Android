package com.my.version.feature.cover.record

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.domain.repository.RecordLocalRepository
import com.my.version.feature.cover.record.state.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordLocalRepository: RecordLocalRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(RecordUiState())
    val uiState get() = _uiState.asStateFlow()

    private var _sideEffect: MutableSharedFlow<RecordSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    fun updateIsRecording(isRecording: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isRecording = isRecording
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startRecording() = viewModelScope.launch {
        recordLocalRepository.setMediaRecorder(Environment.DIRECTORY_RECORDINGS)
        recordLocalRepository.startRecording()
        updateIsRecording(true)
    }

    fun stopRecording() = viewModelScope.launch {
        if(_uiState.value.isRecording) {
            recordLocalRepository.stopRecording()
            updateIsRecording(false)
            _sideEffect.emit(RecordSideEffect.NavigateUp)
        }
    }

}