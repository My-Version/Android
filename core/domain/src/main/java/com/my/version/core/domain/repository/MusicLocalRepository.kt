package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.CoverAudioFile
import java.io.InputStream

interface MusicLocalRepository {
    suspend fun getMusicAudioList(): List<CoverAudioFile>
    suspend fun writeMusicAudio(fileName: String,inputStream: InputStream)
}