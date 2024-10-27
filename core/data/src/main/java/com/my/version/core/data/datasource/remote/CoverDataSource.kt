package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.response.CoverListResponse

interface CoverDataSource {
    suspend fun getCoverList(): List<CoverListResponse>
}