package com.my.version.core.data.repositoryimpl

import android.media.MediaRecorder
import com.my.version.core.data.datasource.local.RecordDataSource
import com.my.version.core.domain.repository.RecordRepository
import timber.log.Timber
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource
): RecordRepository {
    private var mediaRecorder: MediaRecorder? = null
    private var filePath: String? = null
    private var isRecording = false
    private var isPaused = false

    override fun initMediaRecorder(type: String?) {
        mediaRecorder = recordDataSource.createNewMediaRecord()
        filePath = recordDataSource.getNewRecordingFileAbsolutePath(type)

        mediaRecorder?.let { recorder ->
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            recorder.setOutputFile(filePath)
            recorder.prepare()
            Timber.tag("RecordTest").d("Initialized Recording")
        }
    }

    override fun getFilePath(): String? = filePath


    override fun startRecording() {
        if(!isRecording && mediaRecorder != null) {
            Timber.tag("RecordTest").d("Started Recording")
            mediaRecorder?.start()
            isRecording = true
        }
    }

    override fun resumeRecording() {
        if(isPaused && mediaRecorder != null) {
            mediaRecorder?.resume()
            isPaused = false
            isRecording = true
        }
    }


    override fun pauseRecording() {
        if(isRecording && !isPaused && mediaRecorder != null) {
            mediaRecorder?.pause()
            isPaused = true
        }
    }

    override fun stopRecording() {
        if(isRecording && mediaRecorder != null) {
            try {
                Timber.tag("RecordTest").d("Stopped Recording")
                mediaRecorder?.stop()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            } finally {
                mediaRecorder?.reset()
                mediaRecorder?.release()
                mediaRecorder = null

                isRecording = false
                isPaused = false

                if(mediaRecorder == null) {
                    Timber.tag("RecordTest").d("Released Recording")
                }
            }
        }
    }
}