package com.my.version.feature.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudio
import com.my.version.core.domain.repository.MusicRepository
import com.my.version.feature.home.BuildConfig.STREAM_URL
import com.my.version.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getMusicList()
    }

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

    fun onMusicSelected(selectedMusic: MusicAudio) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                currentMusic = selectedMusic
            )
        }

        Timber.tag("STREAMING").d("play => $STREAM_URL${selectedMusic.audio} ")
        val uri =
            Uri.parse(STREAM_URL + selectedMusic.audio)

        uri?.run {
            startPlayer(this)
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

    fun startPlayer(uri: Uri) = viewModelScope.launch {
        _sideEffect.emit(HomeSideEffect.StartMusic(uri))
        updateIsMusicPlaying(true)
    }

    fun playPlayer() = viewModelScope.launch {
        _sideEffect.emit(HomeSideEffect.PlayMusic)
        updateIsMusicPlaying(true)
    }

    fun pausePlayer() = viewModelScope.launch {
        _sideEffect.emit(HomeSideEffect.PauseMusic)
        updateIsMusicPlaying(false)
    }
}

