package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.domain.repository.CoverUploadRepository
import java.io.File
import javax.inject.Inject

class CoverUploadRepositoryImpl @Inject constructor(
    private val coverDataSource: CoverDataSource
) : CoverUploadRepository {
    override suspend fun uploadCover(
        file: File,
        userId: String,
        musicName: String
    ): Result<Boolean> = runCatching {
        coverDataSource.postCoverUpload(file, userId, musicName)
    }
}