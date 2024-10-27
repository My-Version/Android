package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.response.MusicListResponse

interface MusicDataSource {
    suspend fun getMusicList(): List<MusicListResponse>
}