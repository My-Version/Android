package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.dto.response.CoverListResponse
import com.my.version.core.data.service.CoverService
import javax.inject.Inject

class CoverDataSourceImpl @Inject constructor(
    private val coverService: CoverService,
) : CoverDataSource {
    override suspend fun getCoverList(): List<CoverListResponse> =
        coverService.getCoverList(bucket = BUCKET)

    companion object {
        private const val BUCKET = "cover"
    }
}