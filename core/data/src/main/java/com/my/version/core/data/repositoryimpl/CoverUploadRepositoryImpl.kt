package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.local.PreferenceUtil
import com.my.version.core.domain.repository.CoverUploadRepository
import java.io.File
import javax.inject.Inject

class CoverUploadRepositoryImpl @Inject constructor(
    private val coverDataSource: CoverDataSource,
    private val preference: PreferenceUtil
) : CoverUploadRepository {
    override suspend fun uploadCover(
        file: File,
        artist: String,
        music: String
    ): Result<Boolean> = runCatching {
        val userId = preference.idToken

        coverDataSource.postCoverUpload(file, userId, artist, music)
    }
}