package com.my.version.core.data.service

import android.content.Context
import android.os.Environment
import com.my.version.core.common.extension.isAudioFile
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Singleton

@Singleton
class LocalFileService(
    @ActivityContext private val context: Context
) {
    fun getCoverAudioFiles(type: String? = null): List<File> {
        val externalStorage = context.getExternalFilesDirs(type).firstOrNull()
        return if (externalStorage != null && isExternalStorageReadable(externalStorage)) {


            val coverList = mutableListOf<File>()
            coverList.apply {
                externalStorage.listFiles()?.forEach { file ->
                    if (file.isAudioFile()) {
                        add(file)
                    } else {
                        Timber.tag("LocalFile").d("${file.path} Not Audio File")
                    }
                }
            }
        } else {
            emptyList()
        }
    }

    suspend fun writeAudioFile(type: String, file: String, inputStream: InputStream) {
        if (isExternalStorageWritable()) {
            val externalStorage = context.getExternalFilesDirs(type)

            val writeFile = File(externalStorage[0], file)



            withContext(Dispatchers.IO) {
                FileOutputStream(writeFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

        }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(path: File): Boolean {
        return Environment.getExternalStorageState(path) in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

}