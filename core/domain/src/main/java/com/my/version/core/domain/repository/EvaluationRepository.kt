package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.EvaluationDetail

interface EvaluationRepository {
    suspend fun getEvaluationList(): Result<List<EvaluationDetail>>
}