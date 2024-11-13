package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.dto.response.EvaluationListResponse
import com.my.version.core.data.service.EvaluationService
import javax.inject.Inject

class EvaluationDataSourceImpl @Inject constructor(
    private val service: EvaluationService
) : EvaluationDataSource {
    override suspend fun fetchEvaluationList(userId: String): List<EvaluationListResponse> =
        service.fetchEvaluationList(userId)

    override suspend fun postEvaluation(): String {
        //TODO: 평가 업로드 요청
        return "Not Prepared"
    }
}