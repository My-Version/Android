package com.my.version.feature.cover.select

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.feature.cover.select.state.CoverSelectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private var _sideEffect = MutableSharedFlow<CoverSelectSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var mediaPlayer: MediaPlayer? = null

    init {
        getMusicList()
    }

    fun updateSortByIndex(index: Int) = _uiState.update { currentState ->
        currentState.copy(
            sortByIndex = index
        )
    }

    fun updateSheetVisibility(isVisible: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isSortSheetVisible = isVisible
        )
    }

    fun updateSelectedIndex(index: Int) = _uiState.update { currentState ->
        currentState.copy(
            selected = index
        )
    }

    fun onClickMusic(selected: Int) {
        if (_uiState.value.selected != selected) {
            stopMusic()
            mediaPlayer = null
            updateSelectedIndex(selected)
            prepareMediaPlayer(selected)
        }
        playMusic()
    }

    private fun getMusicList() = viewModelScope.launch {
        val musicList = musicLocalRepository.getMusicAudioList()
        _uiState.update { currentState ->
            currentState.copy(
                loadState = UiState.Success(musicList),
            )
        }
    }

    private fun prepareMediaPlayer(index: Int) {
        (_uiState.value.loadState as UiState.Success).takeIf {
            _uiState.value.loadState is UiState.Success
        }?.run {
            val music = this.data[index].audio
            if (music?.absolutePath != null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(music.absolutePath)
                    prepare()
                    setOnCompletionListener {
                        reset()
                    }
                }
            }
        }
    }

    private fun playMusic() {
        mediaPlayer?.run {
            if (this.isPlaying) {
                this.pause()
            } else {
                this.start()
            }
        }
    }

    fun stopMusic() = mediaPlayer?.run {
        if (this.isPlaying) {
            this.reset()
            this.stop()
            this.release()
        }
        mediaPlayer = null
    }


    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.NavigateUp)
    }

    fun navigateToUpload() = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.NavigateNext)
    }
}