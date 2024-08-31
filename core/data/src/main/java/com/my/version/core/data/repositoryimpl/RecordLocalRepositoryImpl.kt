package com.my.version.core.data.repositoryimpl

import android.media.MediaMetadataRetriever
import android.os.Environment
import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.domain.entity.RecordAudioFile
import com.my.version.core.domain.repository.RecordLocalRepository
import java.io.File
import javax.inject.Inject

class RecordLocalRepositoryImpl @Inject constructor(
    private val scopedStorageDataSource: ScopedStorageDataSource
) : RecordLocalRepository {
    override suspend fun getRecordFile(file: File): RecordAudioFile =
        RecordAudioFile(
            title = file.name,
            createdDate = file.lastModified().toString(),
            time = getDuration(file),
            audio = file
        )

    override suspend fun getRecordFilesFromDir(): List<RecordAudioFile> =
        mutableListOf<RecordAudioFile>().apply {
            scopedStorageDataSource.getAudioFileList(type = Environment.DIRECTORY_RECORDINGS)
                .forEach { file ->
                    this.add(getRecordFile(file))
                }
        }.toList()


    override suspend fun clearFilesFromDir() {
        scopedStorageDataSource.clearFileDir(type = Environment.DIRECTORY_RECORDINGS)
    }

    private fun getDuration(file: File): String {
        val retriever = MediaMetadataRetriever()
        var duration: Long? = null
        try {
            retriever.setDataSource(file.absolutePath)
            duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        val minutes = (duration?:0) / 1000 / 60
        val seconds = ((duration?:0) / 1000) % 60
        return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }
}