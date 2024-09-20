package com.my.version.core.data.repositoryimpl

import android.media.MediaRecorder
import com.my.version.core.data.datasource.local.RecordDataSource
import com.my.version.core.domain.repository.RecordRepository
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource
): RecordRepository {
    private var mediaRecorder: MediaRecorder? = null
    private var filePath: String? = null
    private var isRecording = false
    private var isPaused = false

    override fun initMediaRecorder(type: String?) {
        if(!isRecording) {
            mediaRecorder = recordDataSource.createNewMediaRecord()
            filePath = recordDataSource.getNewRecordingFileAbsolutePath(type)

            mediaRecorder?.let { recorder ->
                with(recorder) {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                    setOutputFile(filePath)
                    prepare()
                }
            }
        }
    }

    override fun getFilePath(): String? = filePath


    override fun startRecording() {
        if(!isRecording && mediaRecorder != null) {
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
                mediaRecorder?.stop()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            } finally {
                mediaRecorder?.reset()
                mediaRecorder?.release()
                mediaRecorder = null

                isRecording = false
                isPaused = false
            }
        }
    }
}