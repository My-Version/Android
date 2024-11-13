package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.domain.repository.EvaluationUploadRepository
import java.io.File
import javax.inject.Inject

class EvaluationUploadRepositoryImpl @Inject constructor(
    private val evaluationDataSource: EvaluationDataSource
) : EvaluationUploadRepository {
    override suspend fun uploadEvaluation(file: File, coverId: Long): Result<Boolean> =
        runCatching {

            /**성공 시 "success"를 반환, 실패 시 다양한 문자열을 반환*/
            evaluationDataSource.postEvaluation(file = file, coverId = coverId) != null
        }
}