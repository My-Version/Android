package com.my.version.core.domain.repository

import java.io.File

interface RecordLocalRepository {
    fun setMediaRecorder(type: String?)
    fun startRecording()
    fun stopRecording()
}