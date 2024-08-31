package com.my.version.core.data.datasourceimpl.local

import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.service.ScopedStorageService
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ScopedStorageDataSourceImpl @Inject constructor(
    private val scopedStorageService: ScopedStorageService
): ScopedStorageDataSource {
    override suspend fun getFileByPath(filePath: String): File? {
        TODO("Not yet implemented")
    }

    override suspend fun getAudioFileList(type: String?): List<File> =
        scopedStorageService.getAudioFiles(type)

    override suspend fun writeAudioFile(type: String, filename: String, inputStream: InputStream) =
        scopedStorageService.writeAudioFile(type, filename, inputStream)

    override suspend fun clearFileDir(type: String?) =
        scopedStorageService.removeFiles(type)
}