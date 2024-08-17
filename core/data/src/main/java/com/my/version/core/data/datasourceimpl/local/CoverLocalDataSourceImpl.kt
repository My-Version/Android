package com.my.version.core.data.datasourceimpl.local

import android.content.ContentValues
import android.media.MediaDataSource
import android.provider.MediaStore
import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.data.service.LocalFileService
import com.my.version.core.domain.entity.CoverAudioFile
import javax.inject.Inject

class CoverLocalDataSourceImpl @Inject constructor(
    localFileService: LocalFileService
): CoverLocalDataSource {
    override suspend fun getCoverList(): List<MediaStore.Audio> {
        val value = ContentValues()
        return emptyList()
    }
}