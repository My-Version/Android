package com.my.version.feature.evaluate.upload

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.feature.evaluate.upload.state.EvaluationUploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvaluationUploadViewModel @Inject constructor(

) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationUploadUiState())
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null

    fun initUiState(
        filePath: String,
        songLyrics: Map<Long, String>
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                filePath = filePath,
                songLyrics = songLyrics
            )
        }
        prepareAudio()
    }

    private fun prepareAudio() = viewModelScope.launch(Dispatchers.Main){
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(uiState.value.filePath)
                prepare()
                setOnPreparedListener {
                    _uiState.update { currentState ->
                        currentState.copy(
                            fileLength = it.duration
                        )
                    }
                }
                setOnCompletionListener {
                    reset()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playAudio() = viewModelScope.launch(Dispatchers.Main){
        mediaPlayer?.run {
            if(this.isPlaying) {
                this.pause()
            } else {
                this.start()
            }
        }
    }

    fun stopAudio() = viewModelScope.launch(Dispatchers.Main){
        mediaPlayer?.run {
            this.stop()
            this.release()
        }
        mediaPlayer = null
    }
}