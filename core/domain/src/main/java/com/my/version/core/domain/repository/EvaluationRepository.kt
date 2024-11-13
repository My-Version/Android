package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.EvaluationResult

interface EvaluationRepository {
    suspend fun getEvaluationList(): Result<List<EvaluationResult>>
}