package com.my.version.core.common.watch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
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

    private var isActive = MutableStateFlow(false)

    private var lastTimestamp = 0L


    fun start() {
        if (isActive.value) return

        CoroutineScope(Dispatchers.Main).launch {
            lastTimestamp = System.currentTimeMillis()
            resume()
        }
    }

    suspend fun resume() {
        this@StopWatch.isActive.value = true

        this@StopWatch.isActive.collect { isActive ->
            while (isActive) {
                delay(1000L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
        }
    }

    fun pause() {
        isActive.value = false
    }

    fun reset() {
        isActive.value = false
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00"
    }

    fun startForLyrics() {
        if (isActive.value) return

        Timber.tag("lyrics").d("starting stopwatch for lyrics")

        CoroutineScope(Dispatchers.Default).launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive.value = true

            while (this@StopWatch.isActive.value) {
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