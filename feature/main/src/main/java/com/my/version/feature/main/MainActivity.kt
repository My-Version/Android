package com.my.version.feature.main

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.my.version.core.common.broadcast.DownloadBroadcastReceiver
import com.my.version.core.common.extension.localDownloadManager
import com.my.version.core.designsystem.theme.MyVersionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var downloadBroadcastReceiver: DownloadBroadcastReceiver
    private lateinit var downloadManager: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initDownloadClass()
        registerBroadCastReceiver()

        setContent {
            CompositionLocalProvider(localDownloadManager provides downloadManager) {
                MyVersionTheme {
                    MainScreen()
                }
            }
        }
    }

    private fun initDownloadClass() {
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadBroadcastReceiver = DownloadBroadcastReceiver(downloadManager)
    }

    private fun registerBroadCastReceiver() {
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(downloadBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(downloadBroadcastReceiver, intentFilter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadBroadcastReceiver)
    }
}



