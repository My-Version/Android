package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.MusicDataSource
import com.my.version.core.data.dto.response.MusicListResponse
import com.my.version.core.data.service.MusicService
import javax.inject.Inject

class MusicDataSourceImpl @Inject constructor(
    private val musicService: MusicService
) : MusicDataSource {
    override suspend fun getMusicList(): List<MusicListResponse> =
        musicService.getMusicList(bucket = BUCKET)

    companion object {
        private const val BUCKET = "song"
    }
}