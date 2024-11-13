package com.my.version.core.data.mapper

import com.my.version.core.data.dto.response.EvaluationListResponse
import com.my.version.core.domain.entity.EvaluationDetail
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun EvaluationListResponse.toEvaluationResult(): EvaluationDetail = with(this) {
    val formattedDateString = createdTime?.let {
        val dateString = it.split("_")[0]
        val toLocalDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val toStringFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

        val formattedDate = LocalDate.parse(dateString, toLocalDateFormatter)
        formattedDate.format(toStringFormatter)
    }

    EvaluationDetail(
        title = createdTime ?: "not prepared",
        date = formattedDateString ?: "not prepared",
        similarity = similarityScore?.toInt(),
        mostSimilarPeriod = mostSimilarPeriod?.toDouble(),
        leastSimilarPeriod = leastSimilarPeriod?.toDouble(),
        timeLength = similarityTimeLength?.toInt(),
        imageUrl = imageUrl,
        coverUrl = coverUrl,
        recordUrl = recordUrl
    )
}