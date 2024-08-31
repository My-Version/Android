package com.my.version.core.data.service

import android.content.Context
import android.os.Environment
import com.my.version.core.common.extension.isAudioFile
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Singleton

@Singleton
class ScopedStorageService(
    @ActivityContext private val context: Context
) {
    suspend fun getAudioFiles(type: String? = null): List<File> = coroutineScope {
        val externalStorage = context.getExternalFilesDirs(type).firstOrNull()
        if (externalStorage != null && isExternalStorageReadable(externalStorage)) {
            val coverList = mutableListOf<File>()
            coverList.apply {
                externalStorage.listFiles()?.forEach { file ->
                    if (file.isAudioFile()) {
                        add(file)
                    }
                }
            }.toList()
        } else {
            emptyList()
        }
    }

    suspend fun writeAudioFile(type: String, file: String, inputStream: InputStream) =
        coroutineScope {
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

    suspend fun removeFiles(type: String?) = coroutineScope {
        if (isExternalStorageWritable()) {
            val externalStorage = context.getExternalFilesDir(type)
            externalStorage?.listFiles()?.forEach {
                it.deleteRecursively()
            }
        }
    }

    fun getExternalFilesDir(type: String? = null): File? = context.getExternalFilesDir(type)

    private fun isExternalStorageWritable(): Boolean = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    private fun isExternalStorageReadable(path: File): Boolean = Environment.getExternalStorageState(path) in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
}