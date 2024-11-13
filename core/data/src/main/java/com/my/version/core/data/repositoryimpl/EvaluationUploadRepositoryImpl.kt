package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.local.PreferenceUtil
import com.my.version.core.domain.repository.EvaluationUploadRepository
import java.io.File
import javax.inject.Inject

class EvaluationUploadRepositoryImpl @Inject constructor(
    private val evaluationDataSource: EvaluationDataSource,
    private val preference: PreferenceUtil
) : EvaluationUploadRepository {
    override suspend fun uploadEvaluation(file: File, coverId: Long): Result<Boolean> =
        runCatching {

            evaluationDataSource.postEvaluation(
                file = file,
                coverId = coverId,
                userId = preference.idToken
            ) != null
        }
}