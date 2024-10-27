package com.my.version.core.data.service

import com.my.version.core.data.dto.response.CoverListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverService {
    @GET("listDownload")
    suspend fun getCoverList(@Query("Bucket") bucket: String): List<CoverListResponse>
}