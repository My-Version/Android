package com.my.version.feature.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getMusicList()
    }

    fun getMusicList() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { currentState ->
            currentState.copy(
                loadState = UiState.Success(
                    com.my.version.feature.home.state.tempList1
                )
            )
        }
    }

    fun onMusicSelected(selectedMusic: MusicAudioFile) = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                currentMusic = selectedMusic
            )
        }

        val uri =
            Uri.parse("https://ku-myversion-bucket.s3.ap-northeast-2.amazonaws.com/Ditto.mp3")

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

