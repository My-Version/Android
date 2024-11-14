package com.my.version.feature.evaluate.record

import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.watch.StopWatch
import com.my.version.core.domain.repository.LyricRepository
import com.my.version.core.domain.repository.RecordRepository
import com.my.version.feature.evaluate.BuildConfig.MUSIC_STREAM_URL
import com.my.version.feature.evaluate.R
import com.my.version.feature.evaluate.record.state.EvaluationRecordUiState
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

/**
 * [prepareMusic] : prepares music when the screen is loaded or reset button is clicked
 * [resetPlayers] : initializes MediaPlayer and MediaRecord when the reset button is clicked
 * [startPlayers] : starts MediaPlayer and MediaRecord when the play button is clicked
 * [pausePlayers] : pauses MediaPlayer and MediaRecord when the pause button is clicked
 * [stopPlayers] : stops MediaPlayer and MediaRecord when MediaPlayer reaches end
 */

@HiltViewModel
class EvaluationRecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val lyricRepository: LyricRepository,
    private val streamMediaPlayer: StreamMediaPlayer
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationRecordUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<EvaluationRecordSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var lyricIndex = 0
    private var stopWatch = StopWatch()

    fun prepareMusic(uriString: String) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                musicUri = uriString
            )
        }
        lyricIndex = 0

        streamMediaPlayer.prepareMediaPlayer(
            uri = Uri.parse(MUSIC_STREAM_URL + uriString),
            onBufferingComplete = { onBufferingComplete() },
            onCompletion = { onMediaCompletion() }
        )

    }

    fun prepareLyrics(music: String, artist: String) = viewModelScope.launch {
        lyricRepository.getLyrics(music, artist)
            .onSuccess { lyricMap ->
                _uiState.update { currentState ->
                    currentState.copy(
                        songLyrics = lyricMap
                    )
                }
            }.onFailure {
                _sideEffect.emit(EvaluationRecordSideEffect.ShowToast(R.string.evaluation_record_lyric_fail))
            }
    }

    fun prepareMusicLyrics(lyric: LinkedHashMap<Long, String>) {
        _uiState.update { currentState ->
            currentState.copy(
                songLyrics = lyric
            )
        }
    }

    private fun onBufferingComplete() {
        lyricIndex = 0
        _uiState.update { currentState ->
            currentState.copy(
                isRecordEnabled = true
            )
        }
    }

    private fun onMediaCompletion() {
        Timber.tag("StreamMediaPlayer").d("Stream Completion")
        stopRecording()
        _uiState.update { currentState ->
            currentState.copy(
                currentTimeStamp = 0,
                isNextEnabled = true
            )
        }
    }


    fun startPlayers() = viewModelScope.launch(Dispatchers.Default) {
        if (_uiState.value.isPlaying) return@launch

        if (_uiState.value.isRecordEnabled) {
            updateIsPlaying(true)
            try {
                streamMediaPlayer.playMediaPlayer()
                startRecording()
                stopWatch.startForLyrics()
                manageLyricIndex()
            } catch (e: IndexOutOfBoundsException) {
                stopPlayers()
                e.printStackTrace()
            } catch (e: Exception) {
                stopPlayers()
                e.printStackTrace()
            }
        }
    }


    private fun startRecording() {
        recordRepository.initMediaRecorder(Environment.DIRECTORY_RECORDINGS)
        recordRepository.startRecording()
    }

    private fun manageLyricIndex() {
        while (_uiState.value.isPlaying && (lyricIndex < _uiState.value.songLyrics.keys.size)) {
            if ((_uiState.value.songLyrics.keys.elementAt(lyricIndex)) < stopWatch.timeMillis) {
                updateCurrentTimeStamp(
                    _uiState.value.songLyrics.keys.elementAt(
                        lyricIndex
                    )
                )
                lyricIndex += 1
            }
        }
    }

    private fun updateCurrentTimeStamp(timeMillis: Long) =
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentTimeStamp = timeMillis
                )
            }
        }

    fun resetPlayers() {
        if (_uiState.value.isPlaying) {
            updateIsPlaying(false)
            stopPlayers()
            prepareMusic(_uiState.value.musicUri)
        }
    }

    fun stopPlayers() {
        try {
            stopMusic()
            stopRecording()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopMusic() {
        streamMediaPlayer.endMediaPlayer()
        stopWatch.reset()
    }

    private fun stopRecording() {
        updateIsPlaying(false)
        recordRepository.stopRecording()
        updateCurrentTimeStamp(0)
    }

    private fun updateIsPlaying(isPlaying: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isPlaying = isPlaying
        )
    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(EvaluationRecordSideEffect.NavigateUp)
    }

    fun navigateToUpload() = viewModelScope.launch {
        val filePath = recordRepository.getFilePath()
        filePath?.let { _sideEffect.emit(EvaluationRecordSideEffect.NavigateToUpload(it)) }
    }
}