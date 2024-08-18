package com.my.version.core.data.datasourceimpl.local

import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.data.service.LocalFileService
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class CoverLocalDataSourceImpl @Inject constructor(
    private val localFileService: LocalFileService
): CoverLocalDataSource {
    override suspend fun getCoverList(type: String?): List<File> =
        localFileService.getCoverAudioFiles(type)

    override suspend fun writeAudioFile(type: String, filename: String, inputStream: InputStream) =
        localFileService.writeAudioFile(type, filename, inputStream)
}