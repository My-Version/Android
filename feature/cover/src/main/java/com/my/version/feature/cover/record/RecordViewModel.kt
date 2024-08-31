package com.my.version.feature.cover.record

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.watch.StopWatch
import com.my.version.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {
    private var isRecording by mutableStateOf(false)

    private var _sideEffect: MutableSharedFlow<RecordSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getFilePath(): String = recordRepository.getFilePath()?:""

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
            _sideEffect.emit(RecordSideEffect.NavigateUp)
        }
    }

    fun dismissDialog() = viewModelScope.launch {
        if(isRecording) stopRecording()
        else _sideEffect.emit(RecordSideEffect.NavigateUp)
    }
}