package com.my.version.core.data.service

import android.content.Context
import android.os.Environment
import com.my.version.core.domain.entity.CoverAudioFile
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Singleton
class LocalFileService(
    @ActivityContext private val context: Context
){
    private val externalStorage = context.getExternalFilesDirs(null)

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun accessFile(fileName: String): File = File(externalStorage.toString(), fileName)

    fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), albumName)
        if (!file.mkdirs()) {
            Timber.tag("LocalFileService").e( "Directory not created")
        }
        return file
    }



    /*fun getCoverAudioFileList(): List<CoverAudioFile> {

    }*/
}