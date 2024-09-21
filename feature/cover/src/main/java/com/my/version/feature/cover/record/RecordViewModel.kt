package com.my.version.feature.cover.record

import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of RecordDialog Screen
 *
 * [startRecording] is called when start button is clicked and starts recording
 * [stopRecording] is called when recording is in progress and stop button is clicked.
 *                  It stops recording and returns file path to previous screen.
 * [dismissDialog] is called when dialog is closed by clicking outside or back button.
 *                  It is manipulated to return empty string and will not show any new content on previous screen.
 */

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    var isRecording by mutableStateOf(false)
    private var _filePath by mutableStateOf("")

    private var _sideEffect: MutableSharedFlow<RecordSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getFilePath(): String {
        _filePath = recordRepository.getFilePath()?:""
        return _filePath
    }

    fun startRecording() = viewModelScope.launch {
        if(!isRecording) {
            isRecording = true
            recordRepository.initMediaRecorder(Environment.DIRECTORY_RECORDINGS)
            recordRepository.startRecording()
        }
    }

    fun stopRecording() = viewModelScope.launch {
        if(isRecording) {
            isRecording = false
            recordRepository.stopRecording()
            _sideEffect.emit(RecordSideEffect.StopRecord)
        }
    }

    fun dismissDialog() = viewModelScope.launch {
        if(!isRecording)
            _sideEffect.emit(RecordSideEffect.NavigateUp)
    }
}