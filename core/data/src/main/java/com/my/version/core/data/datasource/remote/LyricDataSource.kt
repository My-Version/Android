package com.my.version.core.data.datasource.remote

interface LyricDataSource {
    suspend fun fetchLyrics(fileName: String): LinkedHashMap<Long, String>
}