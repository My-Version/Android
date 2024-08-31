package com.my.version.feature.cover.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.media.MyVersionMediaPlayer
import com.my.version.core.domain.entity.RecordAudioFile
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.core.domain.repository.RecordLocalRepository
import com.my.version.feature.cover.upload.state.UploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CoverUploadViewModel @Inject constructor(
    private val musicLocalRepository: MusicLocalRepository,
    private val recordLocalRepository: RecordLocalRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(UploadUiState())
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer = MyVersionMediaPlayer()

    fun updateRecordDialogVisibility(visibility: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                recordDialogVisibility = visibility
            )
        }
    }

    fun updateUploadFiles(newFileList: List<RecordAudioFile>) =
        _uiState.update { currentState ->
            currentState.copy(
                uploadFiles = newFileList
            )
        }

    fun addRecordFile(filePath: String) = viewModelScope.launch {
        val file = File(filePath)
        val recordFile = recordLocalRepository.getRecordFile(file)

        updateUploadFiles(
            newFileList = _uiState.value.uploadFiles.toMutableList().apply {
                add(recordFile)
            }
        )
    }

    fun addExternalRecordFile(filePath: String) = viewModelScope.launch {
        val file = File(filePath)

    }

    fun removeRecordFile(index: Int) = updateUploadFiles(
        newFileList = _uiState.value.uploadFiles.toMutableList().apply {
            removeAt(index)
        }
    )

    fun clearRecordFiles() = viewModelScope.launch {
        recordLocalRepository.clearFilesFromDir()
    }

    fun playRecordFile(index: Int) = viewModelScope.launch {
        try {
            val music = uiState.value.uploadFiles[index]
            mediaPlayer.stopMusic()
            mediaPlayer.playMusic(music.audio)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun stopRecordFile() = viewModelScope.launch {
        mediaPlayer.stopMusic()
    }
}