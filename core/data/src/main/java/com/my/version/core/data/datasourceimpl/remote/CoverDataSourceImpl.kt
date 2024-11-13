package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.dto.response.CoverListResponse
import com.my.version.core.data.service.CoverService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class CoverDataSourceImpl @Inject constructor(
    private val coverService: CoverService,
) : CoverDataSource {
    override suspend fun getCoverList(userId: String): List<CoverListResponse> {
        val coverList = coverService.getCoverList(userId = userId)

        return coverList
    }

    override suspend fun postCoverUpload(
        file: File,
        userId: String,
        artist: String,
        music: String
    ): Boolean {
        val filePart = prepareFilePart(file)
        /*val userIdPart = userId.toRequestBody(MIME_TEXT.toMediaTypeOrNull())
        val artistPart = artist.toRequestBody(MIME_TEXT.toMediaTypeOrNull())
        val musicPart = music.toRequestBody(MIME_TEXT.toMediaTypeOrNull())*/

        return coverService.postCoverUpload(
            filePart, userId, artist, music
        )
    }

    private fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody(MIME_AUDIO.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(REQUEST_BODY_FILE, file.name, requestFile)
    }

    companion object {
        private const val MIME_TEXT = "text/plain"
        private const val MIME_AUDIO = "audio/mp4"
        private const val REQUEST_BODY_FILE = "file"
    }
}