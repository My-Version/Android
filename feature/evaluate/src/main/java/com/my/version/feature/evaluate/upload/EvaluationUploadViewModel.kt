package com.my.version.feature.evaluate.upload

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.watch.StopWatch
import com.my.version.feature.evaluate.upload.state.EvaluationUploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EvaluationUploadViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationUploadUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<EvaluationUploadSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var mediaPlayer: MediaPlayer? = null
    private var stopWatch = StopWatch()
    var lyricIndex = 0

    fun initUiState(
        filePath: String,
        songLyrics: Map<Long, String>
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                filePath = filePath,
                songLyrics = songLyrics
            )
        }
        prepareAudio()
    }

    private fun prepareAudio() = viewModelScope.launch(Dispatchers.Main) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(uiState.value.filePath)
                prepare()
                setOnPreparedListener {
                    _uiState.update { currentState ->
                        currentState.copy(
                            fileLength = it.duration
                        )
                    }
                }
                setOnCompletionListener {
                    reset()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playAudio() = viewModelScope.launch(Dispatchers.Main) {
        mediaPlayer?.run {
            if (this.isPlaying) {
                this.pause()
            } else {
                this.start()
                updateProgress()
            }
        }
    }

    fun stopAudio() = viewModelScope.launch(Dispatchers.Main) {
        mediaPlayer?.run {
            this.stop()
            this.release()
        }
        mediaPlayer = null
    }

    fun changeSlider(position: Float) {
        mediaPlayer?.run {
            if (this.isPlaying) {
                val newPosition = position * this.duration
                _uiState.update { currentState ->
                    currentState.copy(
                        progress = position,
                        currentTimeStamp = this.currentPosition.toLong()
                    )
                }
                this.seekTo(newPosition.toInt())
            }
        }
    }

    private fun updateIsPlaying() {
        _uiState.update { currentState ->
            currentState.copy(
                isPlaying = mediaPlayer?.isPlaying ?: false
            )
        }
    }

    suspend fun updateProgress() = withContext(Dispatchers.Default) {
        try {
            while (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.run {
                    with(this.currentPosition) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                progress = this.toFloat() / (this@run.duration.toFloat()),
                            )
                        }

                        if(_uiState.value.songLyrics.keys.contains(this.toLong())) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    currentTimeStamp = this.toLong(),
                                    lyricIndex = _uiState.value.songLyrics.keys.indexOf(this.toLong())
                                )
                            }
                        }
                    }
                }

                Timber.tag("Progress").d("${_uiState.value.progress} <-> ${_uiState.value.currentTimeStamp}")
            }
        } catch (_: Exception) {
            stopAudio()
        }
    }

    private fun seekCurrentLyricIndex() {

    }
}