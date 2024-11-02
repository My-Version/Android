package com.my.version.core.data.datasource.remote

import com.my.version.core.data.dto.response.CoverListResponse
import java.io.File

interface CoverDataSource {
    suspend fun getCoverList(): List<CoverListResponse>
    suspend fun postCoverUpload(
        file: File,
        userId: String,
        musicName: String
    ): String
}