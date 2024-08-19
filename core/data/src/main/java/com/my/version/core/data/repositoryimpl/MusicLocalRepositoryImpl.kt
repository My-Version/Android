package com.my.version.core.data.repositoryimpl

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.my.version.core.data.R
import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.mapper.toCoverAudioFile
import com.my.version.core.data.mapper.toMusicAudioFile
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.entity.MusicAudioFile
import com.my.version.core.domain.repository.MusicLocalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

/**
 * InputStream 예시 -> val stream = context.resources.openRawResource(R.raw.wonderful)
 */

class MusicLocalRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val scopedStorageDataSource: ScopedStorageDataSource
) : MusicLocalRepository {
    private val type = Environment.DIRECTORY_MUSIC

    override suspend fun getMusicAudioList(): List<MusicAudioFile> {
        return scopedStorageDataSource.getCoverList(type = type).map { file ->
            file.toMusicAudioFile() ?: MusicAudioFile()
        }
    }

    override suspend fun writeMusicAudio(
        fileName: String, //파일 이름 형식: title_yyMMdd.mp3
        inputStream: InputStream
    ) {
        scopedStorageDataSource.writeAudioFile(
            type = type,
            inputStream = inputStream,
            filename = fileName
        )
    }

    override suspend fun playAudio(uri: Uri) {
        val mediaPlayer = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(uri)

        with(mediaPlayer) {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    /*override suspend fun writeMusicAudioFile() {
        val stream = context.resources.openRawResource(R.raw.tonight)
        val fileName = "Tonight_BigBang.mp3"

        scopedStorageDataSource.writeAudioFile(
            type = type,
            inputStream = stream,
            filename = fileName
        )
    }*/
}