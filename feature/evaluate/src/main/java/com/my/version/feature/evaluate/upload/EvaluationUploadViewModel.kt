package com.my.version.feature.evaluate.upload

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.evaluate.upload.state.EvaluationUploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EvaluationUploadViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationUploadUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<EvaluationUploadSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var mediaPlayer: MutableStateFlow<MediaPlayer?> = MutableStateFlow(null)

    fun setAudioData(
        filePath: String, songLyrics: Map<Long, String>
    ) = _uiState.update { currentState ->
        currentState.copy(
            filePath = filePath,
            songLyrics = songLyrics
        )
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
            Timber.tag("StreamMediaPlayer").d("isPlaying: ${this.isPlaying}")
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

                    Timber.tag("Progress")
                        .d("${_uiState.value.progress} <-> ${_uiState.value.currentTimeStamp}")
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
}
