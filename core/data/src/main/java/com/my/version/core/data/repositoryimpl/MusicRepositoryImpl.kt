package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.remote.MusicDataSource
import com.my.version.core.domain.entity.MusicAudio
import com.my.version.core.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDataSource: MusicDataSource
) : MusicRepository {
    override suspend fun getMusicList(): Result<List<MusicAudio>> = runCatching {
        musicDataSource.getMusicList().map {
            MusicAudio(
                title = it.music,
                artist = it.artist,
                audio = AUDIO_FORMAT.format(it.music, it.artist)
            )
        }
    }

    companion object {
        private const val AUDIO_FORMAT = "%s-%s.mp3"
    }
}