package com.my.version.core.data.datasourceimpl.local

import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.domain.entity.CoverAudioFile

class CoverLocalDataSourceImpl: CoverLocalDataSource {
    override suspend fun getCoverList(): List<CoverAudioFile> {
        TODO("Not yet implemented")
    }
}