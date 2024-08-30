package com.my.version.core.data.datasourceimpl.local

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.my.version.core.data.datasource.local.RecordDataSource
import com.my.version.core.data.service.ScopedStorageService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: ScopedStorageService
) : RecordDataSource {

    override fun createNewMediaRecord(): MediaRecorder =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

    override fun getNewRecordingFileAbsolutePath(type: String?): String {
        val externalFilesDir = service.getExternalFilesDir(type)
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss")
        val file = File(externalFilesDir, "${currentTime.format(formatter)}.mp4")
        return file.absolutePath
    }


}