package com.my.version.core.data.repositoryimpl

import android.content.Context
import android.os.Environment
import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.data.mapper.toCoverAudioFile
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.repository.CoverLocalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject

/**
 * InputStream 예시 -> val stream = context.resources.openRawResource(R.raw.wonderful)
 */

class CoverLocalRepositoryImpl @Inject constructor(
    private val coverLocalDataSource: CoverLocalDataSource
) : CoverLocalRepository {
    private val type = Environment.DIRECTORY_MUSIC

    override suspend fun getCoverAudioList(): List<CoverAudioFile> {
        return coverLocalDataSource.getCoverList(type = type).map { file ->
            file.toCoverAudioFile() ?: CoverAudioFile()
        }
    }

    override suspend fun writeCoverAudio(
        fileName: String, //파일 이름 형식: title_yyMMdd.mp3
        inputStream: InputStream
    ) {
        coverLocalDataSource.writeAudioFile(
            type = type,
            inputStream = inputStream,
            filename = fileName
        )
    }


}