package com.my.version.feature.cover.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.version.core.common.state.UiState
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import com.my.version.feature.cover.BuildConfig.COVER_STREAM_URL
import com.my.version.feature.cover.BuildConfig.DOWNLOAD_HOST
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

    fun updateIsMusicPlaying(isPlaying: Boolean) = _uiState.update { currentState ->
        currentState.copy(
            isAudioPlaying = isPlaying
        )
    }

    fun startCoverAudio(audio: String) = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.StartCoverAudio(Uri.parse(COVER_STREAM_URL + audio)))
        updateIsMusicPlaying(true)
    }

    fun playPlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.PlayCoverAudio)
        updateIsMusicPlaying(true)
    }

    fun pausePlayer() = viewModelScope.launch {
        _sideEffect.emit(CoverSideEffect.PauseCoverAudio)
        updateIsMusicPlaying(false)
    }

    fun downloadAudio(cover: CoverAudio) = viewModelScope.launch {
        val encodedCoverName = Uri.encode(cover.audio)
        val downloadUri = Uri.parse(
            "$DOWNLOAD_SCHEME://$DOWNLOAD_HOST/$DOWNLOAD_PATH?$DOWNLOAD_QUERY_FILE_NAME=${encodedCoverName}&$DOWNLOAD_QUERY_BUCKET=$DOWNLOAD_QUERY_BUCKET_VALUE"
        )

        _sideEffect.emit(
            CoverSideEffect.DownloadAudio(
                uri = downloadUri,
                outputPath = cover.audio,
                notificationTitle = cover.audio
            )
        )
    }

    companion object {
        private const val DOWNLOAD_SCHEME = "http"
        private const val DOWNLOAD_PATH = "download"
        private const val DOWNLOAD_QUERY_FILE_NAME = "fileName"
        private const val DOWNLOAD_QUERY_BUCKET = "bucketName"
        private const val DOWNLOAD_QUERY_BUCKET_VALUE = "cover"
    }
}