package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.domain.entity.CoverAudio
import com.my.version.core.domain.repository.CoverRepository
import javax.inject.Inject

class CoverRepositoryImpl @Inject constructor(
    private val coverDataSource: CoverDataSource
) : CoverRepository {
    override suspend fun getCoverList(): Result<List<CoverAudio>> = runCatching {
        coverDataSource.getCoverList().map {
            CoverAudio(
                title = it.music,
                createdDate = it.artist,
                audio = AUDIO_FORMAT.format(it.artist)
            )
        }
    }

    companion object {
        private const val AUDIO_FORMAT = "%s.wav"
    }
}