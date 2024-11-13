package com.my.version.core.data.service

import com.my.version.core.data.dto.response.EvaluationListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EvaluationService {
    @GET("compareList")
    suspend fun fetchEvaluationList(
        @Query("userId") userId: String
    ): List<EvaluationListResponse>
}