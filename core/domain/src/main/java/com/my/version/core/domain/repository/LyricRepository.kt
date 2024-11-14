package com.my.version.core.domain.repository

interface LyricRepository {
    suspend fun getLyrics(music: String, artist: String): LinkedHashMap<Long, String>
}