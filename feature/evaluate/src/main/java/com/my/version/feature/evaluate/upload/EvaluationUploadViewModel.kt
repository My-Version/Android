package com.my.version.feature.evaluate.upload

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.repository.EvaluationUploadRepository
import com.my.version.core.domain.repository.LyricRepository
import com.my.version.feature.evaluate.upload.state.EvaluationUploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EvaluationUploadViewModel @Inject constructor(
    private val evaluationUploadRepository: EvaluationUploadRepository,
    private val lyricRepository: LyricRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationUploadUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<EvaluationUploadSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var mediaPlayer: MutableStateFlow<MediaPlayer?> = MutableStateFlow(null)

    fun updateFilePath(
        filePath: String
    ) = _uiState.update { currentState ->
        currentState.copy(
            filePath = filePath
        )
    }

    fun getLyrics(music: String, artist: String) = viewModelScope.launch {
        lyricRepository.getLyrics(music, artist)
            .onSuccess { lyricMap ->
                _uiState.update { currentState ->
                    currentState.copy(
                        songLyrics = lyricMap
                    )
                }
            }.onFailure {
                it.printStackTrace()
            }
    }

    fun prepareAudio() {
        try {
            mediaPlayer.value = MediaPlayer().apply {
                setDataSource(_uiState.value.filePath)
                prepare()
                setOnPreparedListener { onAudioPrepared(it.duration) }
                setOnCompletionListener { onAudioCompletion() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onAudioPrepared(duration: Int) = _uiState.update { currentState ->
        currentState.copy(
            fileLength = duration,
            progress = 0f,
            currentTimeStamp = 0L,
            lyricIndex = 0
        )
    }

    private fun onAudioCompletion() = _uiState.update { currentState ->
        currentState.copy(
            progress = 0f,
            currentTimeStamp = 0L,
            lyricIndex = 0,
            isPlaying = false
        )
    }

    fun playAudio() {
        mediaPlayer.value?.run {
            if (this.isPlaying) {
                this.pause()
                updateIsPlaying(false)
            } else {
                this.start()
                updateIsPlaying(true)
                updateProgress()
            }
        }
    }


    fun stopAudio() {
        mediaPlayer.value?.run {
            this.stop()
            this.release()
        }
        mediaPlayer.value = null
    }

    fun changeSlider(position: Float) {
        mediaPlayer.value?.run {
            val newPosition = position * this.duration
            _uiState.update { currentState ->
                currentState.copy(
                    progress = position, currentTimeStamp = this.currentPosition.toLong()
                )
            }
            this.seekTo(newPosition.toInt())

        }
    }

    private fun updateProgress() = viewModelScope.launch(Dispatchers.Default) {
        try {
            mediaPlayer.collect { mediaPlayer ->
                while (mediaPlayer?.isPlaying == true) {
                    mediaPlayer.run {
                        with(this.currentPosition) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    progress = this.toFloat() / (this@run.duration.toFloat()),
                                )
                            }

                            if (_uiState.value.songLyrics.keys.contains(this.toLong())) {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        currentTimeStamp = this.toLong(),
                                        lyricIndex = _uiState.value.songLyrics.keys.indexOf(this.toLong())
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } catch (_: Exception) {
            stopAudio()
        }
    }

    private fun updateIsPlaying(isPlaying: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isPlaying = isPlaying
        )
    }

    fun updateUploadDialogVisibility(visibility: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                uploadDialogVisibility = visibility
            )
        }
    }

    fun uploadFilesForEvaluation(coverId: Long) = viewModelScope.launch {
        updateUploadDialogState(UiState.Loading)

        val file = File(_uiState.value.filePath)
        evaluationUploadRepository.uploadEvaluation(file = file, coverId = coverId)
            .onSuccess {
                updateUploadDialogState(UiState.Success("Success"))
            }
            .onFailure { message ->
                message.printStackTrace()
                updateUploadDialogState(UiState.Failure("Fail"))
            }
    }

    fun updateUploadDialogState(state: UiState<String>) =
        _uiState.update { currentState ->
            currentState.copy(
                dialogLoadState = state
            )
        }
}
