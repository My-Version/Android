package com.my.version.feature.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudio
import com.my.version.core.domain.repository.MusicRepository
import com.my.version.feature.home.BuildConfig.STREAM_URL
import com.my.version.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val streamMediaPlayer: StreamMediaPlayer
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getMusicList() = viewModelScope.launch {
        musicRepository.getMusicList()
            .onSuccess { musicList ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UiState.Success(musicList)
                    )
                }
            }
            .onFailure {
                _sideEffect.emit(HomeSideEffect.ShowToast(R.string.home_toast_music_error))
            }
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

    fun updateIsMusicPlaying(isPlaying: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isMusicPlaying = isPlaying
        )
    }

    fun updateProgress(progress: Float) = _uiState.update { currentState ->
        currentState.copy(
            audioProgress = progress
        )
    }

    fun onMusicSelected(selectedMusic: MusicAudio) {
        if (selectedMusic.audio != _uiState.value.currentMusic?.audio) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentMusic = selectedMusic
                )
            }
            prepareMusicAudio(selectedMusic.audio)
        }
    }

    private fun prepareMusicAudio(audio: String) = viewModelScope.launch {
        streamMediaPlayer.endMediaPlayer()
        updateProgress(0f)

        streamMediaPlayer.prepareMediaPlayer(
            uri = Uri.parse(STREAM_URL + audio),
            onPrepared = {
                updateIsMusicPlaying(true)
                playPlayer()
            },
            onCompletion = {
                _uiState.update { currentState ->
                    currentState.copy(
                        isMusicPlaying = false,
                        audioProgress = 0f,
                        currentMusic = null
                    )
                }
                stopPlayer()
            }
        )
    }

    fun playPlayer() = viewModelScope.launch {
        streamMediaPlayer.playMediaPlayer()
        updateIsMusicPlaying(true)
        proceedProgress()
    }

    fun pausePlayer() = viewModelScope.launch {
        streamMediaPlayer.pauseMediaPlayer()
        updateIsMusicPlaying(false)
    }

    fun stopPlayer() = viewModelScope.launch {
        streamMediaPlayer.endMediaPlayer()
        _uiState.update { currentState ->
            currentState.copy(
                isMusicPlaying = false,
                audioProgress = 0f,
                currentMusic = null
            )
        }
    }

    fun seekPlayer(progress: Float) {
        val duration = streamMediaPlayer.getMediaPlayerDuration()
        val newSeek = (duration * progress).toInt()
        streamMediaPlayer.seekInMediaPlayer(newSeek)
        _uiState.update { currentState ->
            currentState.copy(
                audioProgress = progress
            )
        }
    }

    private suspend fun proceedProgress() = withContext(Dispatchers.Default) {
        try {
            while (_uiState.value.isMusicPlaying) {
                val progress = streamMediaPlayer.getMediaPlayerProgress()
                updateProgress(progress)
            }
        } catch (_: Exception) {
        }
    }


}

