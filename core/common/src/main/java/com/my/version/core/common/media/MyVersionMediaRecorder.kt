package com.my.version.core.common.media

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyVersionMediaRecorder @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val mediaRecorder: MediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        MediaRecorder(context)
    } else {
        MediaRecorder()
    }

    init {
        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        }
    }

    fun setOutputFilePath(filePath: String) {
        mediaRecorder.setOutputFile(filePath)
        mediaRecorder.prepare()
    }
}