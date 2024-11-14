package com.my.version.core.domain.repository

interface LyricRepository {
    suspend fun getLyrics(music: String, artist: String): Result<LinkedHashMap<Long, String>>
}