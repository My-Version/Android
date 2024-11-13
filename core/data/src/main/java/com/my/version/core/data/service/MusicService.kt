package com.my.version.core.data.service

import com.my.version.core.data.dto.response.MusicListResponse
import retrofit2.http.GET

interface MusicService {
    @GET("songList")
    suspend fun getMusicList(): List<MusicListResponse>
}