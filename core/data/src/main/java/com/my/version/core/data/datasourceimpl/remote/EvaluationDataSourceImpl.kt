package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.dto.response.EvaluationListResponse
import com.my.version.core.data.service.EvaluationService
import timber.log.Timber
import javax.inject.Inject

class EvaluationDataSourceImpl @Inject constructor(
    private val service: EvaluationService
) : EvaluationDataSource {
    override suspend fun fetchEvaluationList(userId: String): List<EvaluationListResponse> {
        Timber.tag("EvaluationRepository").d("evaluationList fetching")
        val response = service.fetchEvaluationList(userId)

        Timber.tag("EvaluationRepository").d("evaluationList fetched $response")
        return response
    }

    override suspend fun postEvaluation(): String {
        //TODO: 평가 업로드 요청
        return "Not Prepared"
    }
}