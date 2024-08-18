package com.my.version.core.data.repositoryimpl

import android.os.Environment
import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.mapper.toCoverAudioFile
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.repository.MusicLocalRepository
import java.io.InputStream
import javax.inject.Inject

/**
 * InputStream 예시 -> val stream = context.resources.openRawResource(R.raw.wonderful)
 */

class MusicLocalRepositoryImpl @Inject constructor(
    private val scopedStorageDataSource: ScopedStorageDataSource
) : MusicLocalRepository {
    private val type = Environment.DIRECTORY_MUSIC

    override suspend fun getMusicAudioList(): List<CoverAudioFile> {
        return scopedStorageDataSource.getCoverList(type = type).map { file ->
            file.toCoverAudioFile() ?: CoverAudioFile()
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


}