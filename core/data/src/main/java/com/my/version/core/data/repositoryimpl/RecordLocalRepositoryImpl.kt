package com.my.version.core.data.repositoryimpl

import android.media.MediaRecorder
import android.os.Environment
import android.provider.MediaStore.Audio.Media
import com.my.version.core.data.datasource.local.RecordDataSource
import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.domain.repository.RecordLocalRepository
import java.io.File
import javax.inject.Inject

class RecordLocalRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource
): RecordLocalRepository {
    private val mediaRecorder = recordDataSource.createNewMediaRecord()

    override fun setMediaRecorder(type: String?) {
        val filePath = recordDataSource.getNewRecordingFileAbsolutePath(type)
        with(mediaRecorder) {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(filePath)
        }
    }

    override fun startRecording() {
        mediaRecorder.prepare()
        mediaRecorder.start()
    }

    override fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }

}