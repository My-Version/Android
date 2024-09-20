package com.my.version.core.common.watch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class StopWatch {
    var formattedTime by mutableStateOf("00:00")
    var timeMillis = 0L

    var isActive = false

    private var lastTimestamp = 0L


    fun start() {
        if(isActive) return

        CoroutineScope(Dispatchers.Main).launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true

            while(this@StopWatch.isActive) {
                delay(1000L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
        }
    }

    fun pause() {
        isActive = false
    }

    fun reset() {
        isActive = false
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00"
    }

    fun startForLyrics() {
        if(isActive) return

        Timber.tag("lyrics").d("starting stopwatch for lyrics")

        CoroutineScope(Dispatchers.Default).launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true

            while(this@StopWatch.isActive) {
                timeMillis = System.currentTimeMillis() - lastTimestamp
            }
        }
    }


    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }
}