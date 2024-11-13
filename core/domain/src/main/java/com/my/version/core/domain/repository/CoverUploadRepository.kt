package com.my.version.core.domain.repository

import java.io.File

interface CoverUploadRepository {
    suspend fun uploadCover(file: File, artist: String, music: String): Result<Boolean>
}