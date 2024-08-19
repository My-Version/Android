package com.my.version.feature.cover.upload

import androidx.lifecycle.ViewModel
import com.my.version.feature.cover.upload.state.UploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CoverUploadViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(UploadUiState())
    val uiState = _uiState.asStateFlow()

    fun addAudioFile(file: File) {
        val fileList = _uiState.value.uploadedFiles.toMutableList().apply {
            add(file)
        }

        _uiState.update { currentState ->
            currentState.copy(
                uploadedFiles = fileList.toList()
            )
        }
    }

    fun removeAudioFile(index: Int) {
        val fileList = _uiState.value.uploadedFiles.toMutableList().apply {
            removeAt(index)
        }

        _uiState.update { currentState ->
            currentState.copy(
                uploadedFiles = fileList.toList()
            )
        }
    }
}