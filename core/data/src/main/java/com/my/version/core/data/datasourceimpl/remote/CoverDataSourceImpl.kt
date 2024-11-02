package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.dto.response.CoverListResponse
import com.my.version.core.data.service.CoverService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class CoverDataSourceImpl @Inject constructor(
    private val coverService: CoverService,
) : CoverDataSource {
    override suspend fun getCoverList(): List<CoverListResponse> =
        coverService.getCoverList(bucket = BUCKET)

    override suspend fun postCoverUpload(
        file: File,
        userId: String,
        musicName: String
    ): String {
        Timber.tag("Uploading").d("Upload called")
        val msg = coverService.postCoverUpload(prepareFilePart(file))//, userId, musicName)

        Timber.tag("Uploading").d("Upload ended $msg")
        return msg.toString()
    }

    private fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("audio/mp4".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    companion object {
        private const val BUCKET = "cover"
    }
}