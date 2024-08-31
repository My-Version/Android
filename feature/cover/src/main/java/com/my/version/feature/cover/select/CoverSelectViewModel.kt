package com.my.version.feature.cover.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer = MyVersionMediaPlayer()

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
            mediaPlayer.stopMusic()
            mediaPlayer.playMusic(music.audio)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopMusic() {
        mediaPlayer.stopMusic()
    }
}