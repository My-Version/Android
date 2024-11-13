package com.my.version.core.data.mapper

import com.my.version.core.data.dto.response.EvaluationListResponse
import com.my.version.core.domain.entity.EvaluationResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun EvaluationListResponse.toEvaluationResult(): EvaluationResult = with(this) {
    val dateString = title.split("_")[0]
    val toLocalDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val toStringFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    val formattedDate = LocalDate.parse(dateString, toLocalDateFormatter)
    val formattedDateString = formattedDate.format(toStringFormatter)
    
    EvaluationResult(
        title = title,
        date = formattedDateString,
        similarity = similarityScore.toInt(),
        mostSimilarPeriod = mostSimilarPeriod,
        leastSimilarPeriod = leastSimilarPeriod,
        timeLength = similarityTimeLength,
        imageUrl = imageUrl
    )
}