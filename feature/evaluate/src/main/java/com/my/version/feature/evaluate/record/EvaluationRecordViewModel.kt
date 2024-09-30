package com.my.version.feature.evaluate.record

import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.media.MusicPlayer
import com.my.version.core.common.watch.StopWatch
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.core.domain.repository.RecordRepository
import com.my.version.feature.evaluate.record.state.EvaluationRecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationRecordViewModel @Inject constructor(
    private val musicLocalRepository: MusicLocalRepository,
    private val recordRepository: RecordRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationRecordUiState())
    val uiState = _uiState.asStateFlow()

    private var isRecording by mutableStateOf(false)
    private var stopWatch = StopWatch()


    init {
        viewModelScope.launch {
            val musicList = musicLocalRepository.getMusicAudioList()
            updateMusic(musicList[3])
        }
    }

    fun updateMusic(music: MusicAudioFile) = _uiState.update { currentState ->
        currentState.copy(
            music = music
        )
    }

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

    fun playMusic() = viewModelScope.launch(Dispatchers.Default) {
        if (isRecording) return@launch

        try {
            MusicPlayer.playMusic(
                file = _uiState.value.music?.audio,
                setOnCompletion = {
                    stopRecording()
                    stopMusic()
                    updateCurrentTimeStamp(0)
                    updateIsNextEnabled(true)
                }
            )
            var lyricIndex = 0
            if (MusicPlayer.isPlaying())
                stopWatch.startForLyrics()

            while (MusicPlayer.isPlaying()) {
                    if ((_uiState.value.songLyrics.keys.elementAt(lyricIndex)) < stopWatch.timeMillis) {
                        updateCurrentTimeStamp(
                            _uiState.value.songLyrics.keys.elementAt(
                                lyricIndex
                            )
                        )
                        lyricIndex += 1
                    }
                }

        }catch (e: IndexOutOfBoundsException) {
            stopWatch.reset()
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun stopMusic() = viewModelScope.launch(Dispatchers.Default) {
        if (isRecording) {
            MusicPlayer.stopMusic()
            stopWatch.reset()
        }
    }

    fun startRecording() = viewModelScope.launch(Dispatchers.IO) {
        if (!isRecording) {
            isRecording = true
            recordRepository.initMediaRecorder(Environment.DIRECTORY_RECORDINGS)
            recordRepository.startRecording()
        }
    }

    fun stopRecording() = viewModelScope.launch(Dispatchers.IO) {
        if (isRecording) {
            isRecording = false
            recordRepository.stopRecording()
            updateCurrentTimeStamp(0)
        }
    }

    fun getRecordFilePath(): String? = recordRepository.getFilePath()
}