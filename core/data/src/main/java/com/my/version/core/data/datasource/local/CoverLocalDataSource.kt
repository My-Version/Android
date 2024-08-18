package com.my.version.core.data.datasource.local

import android.media.MediaDataSource
import android.provider.MediaStore
import com.my.version.core.domain.entity.CoverAudioFile
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

interface CoverLocalDataSource {
    suspend fun getCoverList(type: String? = null): List<File>
    suspend fun writeAudioFile(type: String, filename: String, inputStream: InputStream)
}