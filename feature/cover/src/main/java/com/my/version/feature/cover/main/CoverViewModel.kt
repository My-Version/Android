package com.my.version.feature.cover.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import com.my.version.core.domain.repository.TokenRepository
import com.my.version.feature.cover.BuildConfig.DOWNLOAD_HOST
import com.my.version.feature.cover.main.state.CoverUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoverViewModel @Inject constructor(
    private val coverRepository: CoverRepository,
    private val tokenRepository: TokenRepository,
    private val streamMediaPlayer: StreamMediaPlayer
) : ViewModel() {
    private val _uiState = MutableStateFlow(CoverUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<CoverSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getCoverList() = viewModelScope.launch {
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
                Timber.tag("CoverResult").d(message = it.message.toString())
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
            isAudioPlaying = isPlaying
        )
    }

    fun updateProgress(progress: Float) = _uiState.update { currentState ->
        currentState.copy(
            audioProgress = progress
        )
    }

    //오디오 재생 버튼을 클릭한 경우
    fun onCoverSelected(selectedCover: CoverAudio) {
        if (selectedCover.audio != _uiState.value.currentAudio?.audio) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentAudio = selectedCover
                )
            }
            prepareCoverAudio(selectedCover.audio)
        }
    }

    private fun prepareCoverAudio(audio: String) = viewModelScope.launch {
        streamMediaPlayer.endMediaPlayer()
        updateProgress(0f)

        streamMediaPlayer.prepareMediaPlayer(
            uri = Uri.parse(audio),
            onPrepared = {
                updateIsMusicPlaying(true)
                playPlayer()
            },
            onCompletion = {
                _uiState.update { currentState ->
                    currentState.copy(
                        isAudioPlaying = false,
                        audioProgress = 0f,
                        currentAudio = null
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
                isAudioPlaying = false,
                audioProgress = 0f,
                currentAudio = null
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
            while (_uiState.value.isAudioPlaying) {
                val progress = streamMediaPlayer.getMediaPlayerProgress()
                Timber.tag("StreamMediaPlayer")
                    .d("progress: $progress, playing: ${_uiState.value.isAudioPlaying}")

                updateProgress(progress)
            }
        } catch (_: Exception) {
        }
    }


    fun downloadAudio(cover: CoverAudio) = viewModelScope.launch {
        //val encodedCoverName = Uri.encode(cover.fileName)
        val outputFileName = FILE_FORMAT.format(cover.title, cover.dateString)
        val downloadUri = Uri.parse(
            "$DOWNLOAD_SCHEME://$DOWNLOAD_HOST/$DOWNLOAD_PATH?$DOWNLOAD_QUERY_FILE_NAME=${cover.fileName}"
        )
        _sideEffect.emit(
            CoverSideEffect.DownloadAudio(
                uri = downloadUri,
                outputPath = outputFileName,
                notificationTitle = outputFileName
            )
        )
    }

    companion object {
        private const val FILE_FORMAT = "%s_%s.wav"
        private const val DOWNLOAD_SCHEME = "http"
        private const val DOWNLOAD_PATH = "download"
        private const val DOWNLOAD_QUERY_FILE_NAME = "fileName"
    }
}