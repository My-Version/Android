package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.local.PreferenceUtil
import com.my.version.core.data.mapper.toCoverAudio
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import javax.inject.Inject

class CoverRepositoryImpl @Inject constructor(
    private val coverDataSource: CoverDataSource,
    private val preferenceUtil: PreferenceUtil
) : CoverRepository {

    override suspend fun getCoverList(): Result<List<CoverAudio>> = runCatching {
        coverDataSource.getCoverList(
            userId = preferenceUtil.idToken
        ).map { response ->
            response.toCoverAudio()
        }
    }
}