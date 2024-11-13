package com.my.version.core.data.datasourceimpl.remote

import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.dto.response.EvaluationListResponse
import com.my.version.core.data.service.EvaluationService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class EvaluationDataSourceImpl @Inject constructor(
    private val service: EvaluationService
) : EvaluationDataSource {
    override suspend fun fetchEvaluationList(userId: String): List<EvaluationListResponse> {
        val response = service.fetchEvaluationList(userId)
        return response
    }

    override suspend fun postEvaluation(coverId: Long, file: File): Long {
        Timber.tag("EvaluationUpload").d("coverId: $coverId, file: ${file.absolutePath}")
        val filePart = prepareFilePart(file)
        val coverIdPart = coverId.toString().toRequestBody(MIME_TEXT.toMediaTypeOrNull())

        return service.postEvaluation(
            file = filePart, coverId = coverIdPart
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