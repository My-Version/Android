package com.my.version.core.domain.repository

/**
 * [initMediaRecorder]: initializes MediaRecorder and prepares for recording
 * [startRecording]: starts recording if not already recording or resumes if recording was paused
 * [pauseRecording]: pauses recording if recorder is working
 * [stopRecording]: stops recording and releases resources
 */

interface RecordRepository {
    fun initMediaRecorder(type: String?)
    fun getFilePath(): String?
    fun startRecording()
    fun resumeRecording()
    fun pauseRecording()
    fun stopRecording()
}