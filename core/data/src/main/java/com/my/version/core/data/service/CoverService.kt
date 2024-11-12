package com.my.version.core.data.service

import com.my.version.core.data.dto.response.CoverListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface CoverService {
    @GET("coverList")
    suspend fun getCoverList(@Query("userId") userId: String): List<CoverListResponse>

    @Multipart
    @POST("upload")
    suspend fun postCoverUpload(
        @Part file: MultipartBody.Part,
        @Part("userID") userId: RequestBody,
        @Part("musicName") musicName: RequestBody,
    ): Boolean

}