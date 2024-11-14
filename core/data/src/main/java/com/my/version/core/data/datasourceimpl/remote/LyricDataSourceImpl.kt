package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.common.media.LrcConverter
import com.my.version.core.data.BuildConfig.LYRIC_URL
import com.my.version.core.data.datasource.remote.LyricDataSource
import com.my.version.core.data.service.LyricService
import timber.log.Timber
import javax.inject.Inject

class LyricDataSourceImpl @Inject constructor(
    private val lyricService: LyricService
) : LyricDataSource {
    override suspend fun fetchLyrics(fileName: String): LinkedHashMap<Long, String> {
        /*
        val lyricList = mutableListOf<String>()
        val responseBody = lyricService.getLyric(fileName)
        responseBody.byteStream().use { inputStream ->
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                lyricList.add(line.orEmpty())
            }
        }
        return lyricList
        */

        Timber.tag("Lyrics").d("Lyric Invoked $LYRIC_URL$fileName")
        val responseBody = lyricService.getLyric(fileName)
        val lyricMap = LrcConverter.convertToLyricMap(responseBody.byteStream())

        return lyricMap
    }
}