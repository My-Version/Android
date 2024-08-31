package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.RecordAudioFile
import java.io.File

interface RecordLocalRepository {
    suspend fun getRecordFile(file: File): RecordAudioFile
    suspend fun getRecordFilesFromDir(): List<RecordAudioFile>
    suspend fun clearFilesFromDir()
}