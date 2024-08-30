package com.my.version.feature.cover.select

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.extension.setNewPlayer
import com.my.version.core.common.extension.stopPreviousMusic
import com.my.version.core.common.media.MyVersionMediaPlayer
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.feature.cover.select.state.CoverSelectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoverSelectViewModel @Inject constructor(
    private val musicLocalRepository: MusicLocalRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(CoverSelectUiState())
    //private var mediaPlayer: MediaPlayer? = null
    val uiState = _uiState.asStateFlow()


    init {
        getMusicList()
    }

    fun updateSelectedIndex(selected: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selected = selected
            )
        }
    }

    private fun getMusicList() = viewModelScope.launch {
        val musicList = musicLocalRepository.getMusicAudioList()
        _uiState.update { currentState ->
            currentState.copy(
                musicList = musicList
            )
        }
    }

    fun playMusic(index: Int) {
        try {
            val music = uiState.value.musicList[index]

            with(MyVersionMediaPlayer) {
                stopMusic()
                setMusic(music.audio)
                playMusic()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun releaseMusicPlayer() {
        MyVersionMediaPlayer.stopMusic()
    }
}