package com.my.version.core.data.datasource.local

import android.media.MediaDataSource
import android.provider.MediaStore
import com.my.version.core.domain.entity.CoverAudioFile

interface CoverLocalDataSource {
    suspend fun getCoverList(): List<MediaStore.Audio>
}