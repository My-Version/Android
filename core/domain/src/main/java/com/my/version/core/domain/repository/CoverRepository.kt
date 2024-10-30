package com.my.version.core.domain.repository

import com.my.version.core.domain.entity.CoverAudio

interface CoverRepository {
    suspend fun getCoverList(): Result<List<CoverAudio>>
}