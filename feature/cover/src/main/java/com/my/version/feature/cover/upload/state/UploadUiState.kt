package com.my.version.feature.cover.upload.state

import com.my.version.core.domain.entity.RecordAudioFile
import java.io.File

data class UploadUiState(
    val uploadedFiles: List<File> = emptyList(),
    val uploadFiles: List<RecordAudioFile> = emptyList(),
    val recordDialogVisibility: Boolean = false,
)
