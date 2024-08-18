package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.CoverAudioFile
import java.io.InputStream

interface CoverLocalRepository {
    suspend fun getCoverAudioList(): List<CoverAudioFile>

    suspend fun writeCoverAudio(fileName: String,inputStream: InputStream)
}