package com.my.version.core.data.datasource.local

import java.io.File
import java.io.InputStream

interface ScopedStorageDataSource {
    suspend fun getCoverList(type: String? = null): List<File>
    suspend fun writeAudioFile(type: String, filename: String, inputStream: InputStream)
}