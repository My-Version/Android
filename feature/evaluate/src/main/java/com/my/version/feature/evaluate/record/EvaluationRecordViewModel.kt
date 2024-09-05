package com.my.version.feature.evaluate.record

import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.my.version.core.common.media.MusicPlayer
import com.my.version.core.common.media.MyVersionMediaPlayer
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EvaluationRecordViewModel @Inject constructor(
    private val musicLocalRepository: MusicLocalRepository,
    private val recordRepository: RecordRepository
): ViewModel() {
    private var music: MusicAudioFile? = null
    private var isRecording by mutableStateOf(false)

    init {
        getMusicList()
    }

    private fun getMusicList() = viewModelScope.launch {
        val musicList = musicLocalRepository.getMusicAudioList()
        music = musicList[1]
    }

    fun playMusic() = viewModelScope.launch {
        try {
            MusicPlayer.playMusic(music?.audio)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun stopMusic() = viewModelScope.launch {
        MusicPlayer.stopMusic()
    }

    fun getFilePath(): String = recordRepository.getFilePath()?:""

    fun startRecording() = viewModelScope.launch(Dispatchers.IO) {
        if(!isRecording) {
            isRecording = true
            recordRepository.initMediaRecorder(Environment.DIRECTORY_RECORDINGS)
            recordRepository.startRecording()
        }
    }

    fun stopRecording() = viewModelScope.launch(Dispatchers.IO) {
        if(isRecording) {
            isRecording = false
            recordRepository.stopRecording()
        }
    }
}