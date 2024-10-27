package com.my.version.core.data.service

import com.my.version.core.data.dto.response.MusicListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {
    @GET("listDownload")
    suspend fun getMusicList(@Query("Bucket") bucket: String): List<MusicListResponse>
}