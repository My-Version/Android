package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.response.EvaluationListResponse
import java.io.File

interface EvaluationDataSource {
    suspend fun fetchEvaluationList(userId: String): List<EvaluationListResponse>
    suspend fun postEvaluation(coverId: Long, file: File, userId: String): Long?
}