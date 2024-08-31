package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.mapper.toCoverAudioFile
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.repository.CoverLocalRepository
import java.io.InputStream
import javax.inject.Inject

/**
 * InputStream 예시 -> val stream = context.resources.openRawResource(R.raw.wonderful)
 */

class CoverLocalRepositoryImpl @Inject constructor(
    private val scopedStorageDataSource: ScopedStorageDataSource,
) : CoverLocalRepository {
    private val type = "Cover"

    override suspend fun getCoverAudioList(): List<CoverAudioFile> {
        return scopedStorageDataSource.getAudioFileList(type = type).map { file ->
            file.toCoverAudioFile() ?: CoverAudioFile()
        }
    }

    override suspend fun writeCoverAudio(
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