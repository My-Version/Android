package com.my.version.feature.cover.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import com.my.version.feature.cover.BuildConfig.COVER_STREAM_URL
import com.my.version.feature.cover.main.state.CoverUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoverViewModel @Inject constructor(
    private val coverRepository: CoverRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CoverUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CoverSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getCoverList()
    }

    private fun getCoverList() = viewModelScope.launch {
        coverRepository.getCoverList()
            .onSuccess { coverList ->
                if (coverList.isEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            loadState = UiState.Empty
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(
                            loadState = UiState.Success(coverList)
                        )
                    }
                }
            }
            .onFailure {
                _uiState.update { currentState ->
                    currentState.copy(
                        loadState = UiState.Failure("Error")
                    )
                }
            }
    }

    fun onCoverSelected(selectedCover: CoverAudio) {
        _uiState.update { currentState ->
            currentState.copy(
                currentAudio = selectedCover
            )
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

    fun startCoverAudio(audio: String) = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.StartCoverAudio(Uri.parse(COVER_STREAM_URL + audio)))
    }

    fun playPlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.PlayCoverAudio)
    }

    fun pausePlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.PauseCoverAudio)
    }


}