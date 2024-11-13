package com.my.version.core.domain.repository

import java.io.File

interface EvaluationUploadRepository {
    suspend fun uploadEvaluation(file: File, coverId: Long): Result<Boolean>
}