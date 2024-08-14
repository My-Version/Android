package com.my.version.core.data.datasource.local

import com.my.version.core.domain.entity.CoverAudioFile

interface CoverLocalDataSource {
    suspend fun getCoverList(): List<CoverAudioFile>
}