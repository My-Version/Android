package com.my.version.feature.evaluate.select

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import com.my.version.feature.evaluate.BuildConfig.COVER_STREAM_URL
import com.my.version.feature.evaluate.select.state.EvaluationSelectUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationSelectViewModel @Inject constructor(
    private val coverRepository: CoverRepository,
    private val streamMediaPlayer: StreamMediaPlayer
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationSelectUiState())
    val uiState = _uiState.asStateFlow()

    private var _sideEffect = MutableSharedFlow<EvaluationSelectSideEffect>()
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

    fun updateCurrentCover(coverAudio: CoverAudio?) = _uiState.update { currentState ->
        currentState.copy(
            currentCover = coverAudio
        )
    }

    fun getCoverList() = viewModelScope.launch() {
        coverRepository.getCoverList()
            .onSuccess { data ->
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = if (data.isNotEmpty()) {
                            UiState.Success(data)
                        } else {
                            UiState.Empty
                        }
                    )
                }
            }
            .onFailure {
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UiState.Failure(it.message ?: "")
                    )
                }
            }
    }

    fun onCoverSelected(coverAudio: CoverAudio?) = viewModelScope.launch {
        if (coverAudio?.audio != _uiState.value.currentCover?.audio) {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedCover = coverAudio,
                    currentCover = coverAudio
                )
            }

            stopPlayer()
            preparePlayer(audioName = coverAudio?.audio.orEmpty())
        }
    }

    private fun preparePlayer(audioName: String) = viewModelScope.launch {
        streamMediaPlayer.prepareMediaPlayer(
            uri = Uri.parse(COVER_STREAM_URL + audioName),
            onPrepared = {
                playPlayer()
            },
            onCompletion = {
                stopPlayer()
                updateCurrentCover(null)
            }
        )
    }

    private fun playPlayer() {
        streamMediaPlayer.playMediaPlayer()
    }

    fun stopPlayer() = viewModelScope.launch {
        streamMediaPlayer.endMediaPlayer()

    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(EvaluationSelectSideEffect.NavigateUp)
        initializeUiState()
    }

    fun navigateToRecord() = viewModelScope.launch {
        _uiState.value.selectedCover?.run {
            _sideEffect.emit(EvaluationSelectSideEffect.NavigateNext(this))
        }
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.update { currentState ->
            currentState.copy(
                selected = -1,
                currentCover = null,
                selectedCover = null
            )
        }
    }
}