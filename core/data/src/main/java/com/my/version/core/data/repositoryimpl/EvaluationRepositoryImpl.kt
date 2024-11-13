package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.local.PreferenceUtil
import com.my.version.core.data.mapper.toEvaluationResult
import com.my.version.core.domain.entity.EvaluationDetail
import com.my.version.core.domain.repository.EvaluationRepository
import javax.inject.Inject

class EvaluationRepositoryImpl @Inject constructor(
    private val evaluationDataSource: EvaluationDataSource,
    private val preference: PreferenceUtil
) : EvaluationRepository {
    override suspend fun getEvaluationList(): Result<List<EvaluationDetail>> = runCatching {
        val evaluationList = evaluationDataSource.fetchEvaluationList(
            userId = preference.idToken
        )

        val evaluationResultList = evaluationList.map { evaluationResponse ->
            evaluationResponse.toEvaluationResult()
        }

        evaluationResultList
    }


}