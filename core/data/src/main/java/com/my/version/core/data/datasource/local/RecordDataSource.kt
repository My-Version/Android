package com.my.version.core.data.datasource.local

import android.media.MediaRecorder

interface RecordDataSource {
    fun createNewMediaRecord(): MediaRecorder
    fun getNewRecordingFileAbsolutePath(type: String?): String
}