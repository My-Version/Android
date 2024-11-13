package com.my.version.feature.evaluate.result

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.musicplayer.di.CoverMediaPlayer
import com.my.version.core.common.musicplayer.di.RecordMediaPlayer
import com.my.version.feature.evaluate.result.state.EvaluationResultAudioState
import com.my.version.feature.evaluate.result.state.EvaluationResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EvaluationResultViewModel @Inject constructor(
    @CoverMediaPlayer private val coverMediaPlayer: StreamMediaPlayer,
    @RecordMediaPlayer private val recordMediaPlayer: StreamMediaPlayer,
) : ViewModel() {
    private var _uiState = MutableStateFlow(EvaluationResultUiState())
    val uiState = _uiState.asStateFlow()

    private var _coverState = MutableStateFlow(EvaluationResultAudioState())
    val coverState = _coverState.asStateFlow()

    private var _recordState = MutableStateFlow(EvaluationResultAudioState())
    val recordState = _recordState.asStateFlow()

    private fun getState(type: ResultType): MutableStateFlow<EvaluationResultAudioState> =
        when (type) {
            is ResultType.COVER -> _coverState
            is ResultType.RECORD -> _recordState
        }

    private fun getPlayer(type: ResultType): StreamMediaPlayer = when (type) {
        is ResultType.COVER -> coverMediaPlayer
        is ResultType.RECORD -> recordMediaPlayer
    }

    fun updateUiState(
        title: String,
        createdDate: String,
        similarity: Int,
        mostSimilarPeriod: Double,
        leastSimilarPeriod: Double,
        timeLength: Int,
        coverUrl: String,
        recordUrl: String,
        imageUrl: String
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                createdDate = createdDate,
                similarity = similarity,
                mostSimilarPeriod = mostSimilarPeriod,
                leastSimilarPeriod = leastSimilarPeriod,
                timeLength = timeLength,
                imageUrl = imageUrl
            )
        }
        updateUrl(type = ResultType.COVER, url = coverUrl)
        updateUrl(type = ResultType.RECORD, url = recordUrl)
        preparePlayer(type = ResultType.COVER)
        preparePlayer(type = ResultType.RECORD)
    }

    fun updateUrl(type: ResultType, url: String) {
        getState(type).update { currentState ->
            currentState.copy(
                url = url
            )
        }
    }

    fun updateItemEnable(type: ResultType) {
        getState(type).update { currentState ->
            currentState.copy(
                isExpanded = !currentState.isExpanded
            )
        }
    }

    fun updateIsPlaying(type: ResultType, isPlaying: Boolean) {
        getState(type).update { currentState ->
            currentState.copy(
                isPlaying = isPlaying
            )
        }
    }

    private fun preparePlayer(type: ResultType) {
        val state = getState(type)
        val player = getPlayer(type)

        player.prepareMediaPlayer(uri = Uri.parse(state.value.url), onPrepared = {
            state.update { currentState ->
                currentState.copy(
                    prepared = true
                )
            }
        })
    }


    fun stopPlayer() {
        coverMediaPlayer.endMediaPlayer()
        recordMediaPlayer.endMediaPlayer()
        updateIsPlaying(ResultType.RECORD, false)
        updateIsPlaying(ResultType.COVER, false)
    }

    fun onClickCoverIcon() = viewModelScope.launch {
        try {
            if (_coverState.value.isPlaying) {
                updateIsPlaying(ResultType.COVER, false)
                coverMediaPlayer.pauseMediaPlayer()
            } else {
                recordMediaPlayer.pauseMediaPlayer()
                updateIsPlaying(ResultType.RECORD, false)

                coverMediaPlayer.playMediaPlayer()
                updateIsPlaying(ResultType.COVER, true)


                proceedProgress(ResultType.COVER)
            }
        } catch (e: Exception) {
            stopPlayer()
            preparePlayer(ResultType.COVER)
            preparePlayer(ResultType.RECORD)
        }
    }

    fun onClickRecordIcon() = viewModelScope.launch {
        try {
            if (_recordState.value.isPlaying) {
                updateIsPlaying(ResultType.RECORD, false)
                recordMediaPlayer.pauseMediaPlayer()
            } else {
                coverMediaPlayer.pauseMediaPlayer()
                updateIsPlaying(ResultType.COVER, false)

                recordMediaPlayer.playMediaPlayer()
                updateIsPlaying(ResultType.RECORD, true)


                proceedProgress(ResultType.RECORD)
            }
        } catch (e: Exception) {
            stopPlayer()
            preparePlayer(ResultType.COVER)
            preparePlayer(ResultType.RECORD)
        }
    }

    fun updateProgress(type: ResultType, progress: Float) {
        val state = getState(type)
        val player = getPlayer(type)

        val duration = player.getMediaPlayerDuration()
        val newSeek = (duration * progress).toInt()
        player.seekInMediaPlayer(newSeek)

        state.update { currentState ->
            currentState.copy(
                progress = progress
            )
        }
    }

    private suspend fun proceedProgress(type: ResultType) {
        withContext(Dispatchers.Default) {
            val typeState = getState(type)
            val player = getPlayer(type)
            typeState.collect { state ->
                while (state.isPlaying) {
                    val progress = player.getMediaPlayerProgress()
                    typeState.update { currentState ->
                        currentState.copy(
                            progress = progress
                        )
                    }
                }
            }
        }
    }


    fun seekToSimilarPeriod(type: ResultType, period: Double) {
        val state = getState(type)
        val player = getPlayer(type)

        val duration = player.getMediaPlayerDuration()
        val periodMillis = period * 1000
        val progress = (periodMillis / duration.toDouble()).toFloat()

        player.seekInMediaPlayer(periodMillis.toInt())
        state.update { currentState ->
            currentState.copy(
                progress = progress
            )
        }
    }
}

sealed class ResultType {
    data object COVER : ResultType()
    data object RECORD : ResultType()
}