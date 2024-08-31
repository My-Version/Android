package com.my.version.core.common.watch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class StopWatch {
    var formattedTime by mutableStateOf("00:00")

    private var isActive = false

    private var timeMillis = 0L
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