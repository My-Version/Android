package com.my.version.feature.evaluate.record

import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.watch.StopWatch
import com.my.version.core.domain.repository.RecordRepository
import com.my.version.feature.evaluate.BuildConfig.MUSIC_STREAM_URL
import com.my.version.feature.evaluate.record.state.EvaluationRecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EvaluationRecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val streamMediaPlayer: StreamMediaPlayer
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationRecordUiState())
    val uiState = _uiState.asStateFlow()

    private var isRecording by mutableStateOf(false)
    private var stopWatch = StopWatch()

    fun updateSongLyrics(lyric: LinkedHashMap<Long, String>) = _uiState.update { currentState ->
        currentState.copy(
            songLyrics = lyric
        )
    }

    private fun updateCurrentTimeStamp(timeMillis: Long) =
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentTimeStamp = timeMillis
                )
            }
        }

    private fun updateIsNextEnabled(isEnabled: Boolean) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                isNextEnabled = isEnabled
            )
        }
    }

    private fun updateIsRecordEnabled(isEnabled: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isRecordEnabled = isEnabled
        )
    }

    fun prepareMusic(uriString: String) = viewModelScope.launch {

        val uri = Uri.parse(MUSIC_STREAM_URL + uriString)
        Timber.tag("StreamMediaPlayer").d("Music: $uri")
        streamMediaPlayer.prepareMediaPlayer(uri = uri,
            onPrepared = { Timber.tag("StreamMediaPlayer").d("EvaluationRecord Prepare Complete") },
            onBufferingComplete = {
                Timber.tag("StreamMediaPlayer").d("EvaluationRecord Buffer Complete")
                updateIsRecordEnabled(true)
            },
            onCompletion = {
                Timber.tag("StreamMediaPlayer").d("EvaluationRecord Music Complete")
                stopRecording()
                updateCurrentTimeStamp(0)
                updateIsNextEnabled(true)
            })
    }

    fun onStartRecording() = viewModelScope.launch(Dispatchers.Default) {
        if (isRecording) return@launch

        if (_uiState.value.isRecordEnabled) {
            try {
                streamMediaPlayer.playMediaPlayer()
                isRecording = true
                startRecording()

                var lyricIndex = 0
                stopWatch.startForLyrics()

                while (isRecording) {
                    if ((_uiState.value.songLyrics.keys.elementAt(lyricIndex)) < stopWatch.timeMillis) {
                        updateCurrentTimeStamp(
                            _uiState.value.songLyrics.keys.elementAt(
                                lyricIndex
                            )
                        )
                        lyricIndex += 1
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                stopWatch.reset()
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onStopRecording() {
        stopMusic()
        stopRecording()
    }

    fun stopMusic() {
        if (isRecording) {
            streamMediaPlayer.endMediaPlayer()
            stopWatch.reset()
        }
    }

    private fun startRecording() {
        recordRepository.initMediaRecorder(Environment.DIRECTORY_RECORDINGS)
        recordRepository.startRecording()
    }

    fun stopRecording() {
        if (isRecording) {
            isRecording = false
            recordRepository.stopRecording()
            updateCurrentTimeStamp(0)
        }
    }

    fun getRecordFilePath(): String? = recordRepository.getFilePath()
}