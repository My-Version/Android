package com.my.version.core.data.service

import com.my.version.core.data.dto.response.EvaluationListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface EvaluationService {
    @GET("compareList")
    suspend fun fetchEvaluationList(
        @Query("userId") userId: String
    ): List<EvaluationListResponse>

    @Multipart
    @POST("compareUpload")
    suspend fun postEvaluation(
        @Part file: MultipartBody.Part,
        @Part("coverId") coverId: RequestBody
    ): Long
}