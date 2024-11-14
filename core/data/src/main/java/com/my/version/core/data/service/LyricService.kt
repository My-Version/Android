package com.my.version.core.data.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface LyricService {
    @GET("{fileName}")
    suspend fun getLyric(@Path("fileName") fileName: String): ResponseBody
}