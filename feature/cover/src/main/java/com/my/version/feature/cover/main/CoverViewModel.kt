package com.my.version.feature.cover.main

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.extension.setNewPlayer
import com.my.version.core.common.extension.stopPreviousMusic
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.repository.CoverLocalRepository
import com.my.version.feature.cover.main.state.CoverUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CoverViewModel @Inject constructor(
    private val coverLocalRepository: CoverLocalRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CoverUiState())
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer:MediaPlayer? = null

    init {
        getCoverList()
    }

    private fun getCoverList() = viewModelScope.launch {
        val coverList = coverLocalRepository.getCoverAudioList()
        if(coverList.isEmpty()) {
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

    fun playCoverAudio(cover: File?) {
        if(cover != null) {
                mediaPlayer?.stopPreviousMusic()
                mediaPlayer = MediaPlayer().setNewPlayer(cover.path)
                mediaPlayer?.setOnCompletionListener {
                    it.release()
                    mediaPlayer = null
                }
        }
    }

    fun onCoverSelected(selectedCover: CoverAudioFile) = _uiState.update { currentState ->
        currentState.copy(
            currentAudio = selectedCover
        )
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


}