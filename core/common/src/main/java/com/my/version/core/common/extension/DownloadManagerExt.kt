package com.my.version.core.common.extension

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.compositionLocalOf
import timber.log.Timber
import java.io.File

fun DownloadManager.download(
    downloadUri: Uri, outputPath: String, notificationTitle: String
) = try {
    val downloadPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/$outputPath"
    Timber.tag("DownloadManager").d("download: $downloadUri, outputPath: $outputPath")
    val destinationUri = Uri.fromFile(File(downloadPath))

    val request = DownloadManager.Request(downloadUri).apply {
        setTitle(notificationTitle)
        setDestinationUri(destinationUri)
        setAllowedOverMetered(true)
    }
    enqueue(request)
} catch (e: Exception) {
    e.printStackTrace()
}


val localDownloadManager = compositionLocalOf<DownloadManager> {
    error("DownloadManager not provided")
}

