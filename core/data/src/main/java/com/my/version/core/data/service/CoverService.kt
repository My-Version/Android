package com.my.version.core.data.service

import com.my.version.core.data.dto.response.CoverListResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface CoverService {
    @GET("listDownload")
    suspend fun getCoverList(@Query("bucketName") bucket: String): List<CoverListResponse>

    @Multipart
    @POST("upload")
    suspend fun postCoverUpload(
        @Part file: MultipartBody.Part,
        @Part userId: String,
        @Part musicName: String
    ): Boolean

}