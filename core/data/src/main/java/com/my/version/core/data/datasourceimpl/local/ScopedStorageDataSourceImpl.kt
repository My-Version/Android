package com.my.version.core.data.datasourceimpl.local

import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.service.ScopedStorageService
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ScopedStorageDataSourceImpl @Inject constructor(
    private val scopedStorageService: ScopedStorageService
): ScopedStorageDataSource {
    override suspend fun getCoverList(type: String?): List<File> =
        scopedStorageService.getCoverAudioFiles(type)

    override suspend fun writeAudioFile(type: String, filename: String, inputStream: InputStream) =
        scopedStorageService.writeAudioFile(type, filename, inputStream)
}