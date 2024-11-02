package com.my.version.feature.cover.select

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudio
import com.my.version.core.domain.repository.MusicRepository
import com.my.version.feature.cover.BuildConfig.STREAM_URL
import com.my.version.feature.cover.R
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
    private val musicRepository: MusicRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(CoverSelectUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<CoverSelectSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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

    fun updateCurrentMusic(audio: MusicAudio?) = _uiState.update { currentState ->
        currentState.copy(
            currentMusic = audio
        )
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
                _sideEffect.emit(CoverSelectSideEffect.ShowToast(R.string.cover_toast_music_error))
            }
    }


    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.NavigateUp)
    }

    fun navigateToUpload() = viewModelScope.launch {
        _uiState.value.currentMusic?.run {
            _sideEffect.emit(CoverSelectSideEffect.NavigateNext(this))
        }
    }

    fun onMusicSelected(selectedMusic: MusicAudio) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                currentMusic = selectedMusic
            )
        }

        val uri =
            Uri.parse(STREAM_URL + selectedMusic.audio)

        uri?.run {
            startPlayer(this)
        }
    }

    fun startPlayer(uri: Uri) = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.StartMusic(uri))

    }

    fun playPlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.PlayMusic)
    }

    fun pausePlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSelectSideEffect.PauseMusic)
    }
}