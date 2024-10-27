package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.MusicAudio

interface MusicRepository {
    suspend fun getMusicList(): Result<List<MusicAudio>>
}