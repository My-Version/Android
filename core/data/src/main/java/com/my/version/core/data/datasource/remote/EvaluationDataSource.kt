package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.response.EvaluationListResponse

interface EvaluationDataSource {
    suspend fun fetchEvaluationList(userId: String): List<EvaluationListResponse>
    suspend fun postEvaluation(): String
}