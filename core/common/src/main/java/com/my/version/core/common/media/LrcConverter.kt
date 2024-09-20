package com.my.version.core.common.media

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object LrcConverter {
    fun convertToLyricMap( inputStream: InputStream): LinkedHashMap<Long, String> =
        linkedMapOf<Long, String>().apply {
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.forEachLine { line ->
                val splitStrings = line.split("]")
                val timeInMillis = convertToMillis(splitStrings[0].removePrefix("["))
                val lyricString = splitStrings[1]

                put(timeInMillis, lyricString)
            }
        }


    private fun convertToMillis(timeString: String): Long {
        val parts = timeString.split(":", ".", ignoreCase = false, limit = 4)

        // 시, 분, 초, 밀리초를 개별적으로 파싱
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        val seconds = parts[2].toInt()
        val milliseconds = if (parts.size > 3) parts[3].toInt() else 0

        // 각 값을 밀리초로 변환 후 합산
        val totalMillis = (hours * 3600000L) + (minutes * 60000L) + (seconds * 1000L) + milliseconds*10L
        return totalMillis
    }
}