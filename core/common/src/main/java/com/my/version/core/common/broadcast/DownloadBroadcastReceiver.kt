package com.my.version.core.common.broadcast

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.my.version.core.common.R
import com.my.version.core.common.extension.showToast
import java.io.File

class DownloadBroadcastReceiver(
    private val downloadManager: DownloadManager
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)?.let { reference ->
            val query = DownloadManager.Query()
            query.setFilterById(reference);

            val cursor = downloadManager.query(query)

            if (cursor != null && cursor.moveToFirst()) {

                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
                val columnTitle = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)

                val status = cursor.getInt(columnIndex)
                val reason = cursor.getInt(columnReason)
                val title = cursor.getString(columnTitle)


                cursor.close()

                when (status) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        context?.run {
                            showToast("Download Successful $reason")
                            showDownloadCompleteNotification(this, title)
                        }
                    }

                    DownloadManager.STATUS_PAUSED -> {
                        context?.showToast("Download Paused $reason")
                    }

                    DownloadManager.STATUS_FAILED -> {
                        context?.showToast("Download Failed $reason")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showDownloadCompleteNotification(context: Context?, fileName: String) {
        context?.run {
            val filePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile.toString()
            val file = File(filePath, fileName)

            // 파일 URI 설정
            val fileUri: Uri = FileProvider.getUriForFile(
                this,
                "${this.packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(fileUri, AUDIO_WAV_MIME_TYPE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // URI 권한 부여
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationBuilder = NotificationCompat.Builder(context, DOWNLOAD_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_CONTENT.format(fileName))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationChannel = NotificationChannel(
                DOWNLOAD_CHANNEL_ID,
                "download_channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    companion object {
        private const val AUDIO_WAV_MIME_TYPE = "audio/wav"
        private const val DOWNLOAD_CHANNEL_ID = "download_channel"

        private const val NOTIFICATION_TITLE = "Download Complete"
        private const val NOTIFICATION_CONTENT = "%s is downloaded successfully"

    }


}