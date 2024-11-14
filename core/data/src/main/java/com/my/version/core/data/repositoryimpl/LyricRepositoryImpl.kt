package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.LyricDataSource
import com.my.version.core.domain.repository.LyricRepository
import javax.inject.Inject

class LyricRepositoryImpl @Inject constructor(
    private val lyricDataSource: LyricDataSource
) : LyricRepository {
    override suspend fun getLyrics(music: String, artist: String): LinkedHashMap<Long, String> {
        val fileName = FILE_FORMAT.format(music, artist)
        return lyricDataSource.fetchLyrics(fileName)
    }

    companion object {
        private const val FILE_FORMAT = "%s-%s.lrc"
    }
}