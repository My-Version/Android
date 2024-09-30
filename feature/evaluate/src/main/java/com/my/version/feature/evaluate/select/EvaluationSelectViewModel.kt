package com.my.version.feature.evaluate.select

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.feature.evaluate.select.state.EvaluationSelectUiState
import com.my.version.feature.evaluate.select.state.tempCoverList
import com.my.version.feature.evaluate.select.state.tempCoverList2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationSelectViewModel @Inject constructor(

): ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationSelectUiState())
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null

    init {
        getCoverList()
    }

    fun updateSelectedIndex(selectedIndex: Int) = viewModelScope.launch {
        _uiState.update {
            _uiState.value.copy(
                selected = selectedIndex
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

    fun getCoverList() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update {
            _uiState.value.copy(
                loadState = UiState.Success(
                    data = tempCoverList
                )
                //loadState = UiState.Empty
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

    fun onClickMusic(selected: Int) {
        if (_uiState.value.selected != selected) {
            stopMusic()
            mediaPlayer = null
            updateSelectedIndex(selected)
            prepareMediaPlayer(selected)
        }
        playMusic()
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
}