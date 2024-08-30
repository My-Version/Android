package com.my.version.feature.cover.upload.state

import java.io.File

data class UploadUiState(
    val uploadedFiles: List<File> = emptyList(),
    val recordDialogVisibility: Boolean = false,
)
