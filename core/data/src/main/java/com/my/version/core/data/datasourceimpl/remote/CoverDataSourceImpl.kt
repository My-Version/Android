package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.dto.response.CoverListResponse
import com.my.version.core.data.service.CoverService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class CoverDataSourceImpl @Inject constructor(
    private val coverService: CoverService,
) : CoverDataSource {
    override suspend fun getCoverList(userId: String): List<CoverListResponse> =
        coverService.getCoverList(userId = userId)

    override suspend fun postCoverUpload(
        file: File,
        userId: String,
        musicName: String
    ): Boolean {
        val filePart = prepareFilePart(file)
        val userIdPart = userId.toRequestBody(MIME_TEXT.toMediaTypeOrNull())
        val musicNamePart = musicName.toRequestBody(MIME_TEXT.toMediaTypeOrNull())

        return coverService.postCoverUpload(
            filePart, userIdPart, musicNamePart
        )
    }

    private fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody(MIME_AUDIO.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(REQUEST_BODY_FILE, file.name, requestFile)
    }

    companion object {
        private const val BUCKET = "cover"
        private const val MIME_TEXT = "text/plain"
        private const val MIME_AUDIO = "audio/mp4"
        private const val REQUEST_BODY_FILE = "file"
    }
}