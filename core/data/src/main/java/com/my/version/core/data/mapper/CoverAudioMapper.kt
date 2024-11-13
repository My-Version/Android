package com.my.version.core.data.mapper

import com.my.version.core.data.dto.response.CoverListResponse
import com.my.version.core.domain.entity.CoverAudio
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CoverListResponse.toCoverAudio(): CoverAudio {
    val dateString = this.createdDate.split("_")[0]
    val toLocalDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val toStringFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")


    val formattedDate = LocalDate.parse(dateString, toLocalDateFormatter)
    val formattedDateString = formattedDate.format(toStringFormatter)

    return CoverAudio(
        title = this.music,
        createdDate = formattedDateString,
        audio = this.s3FileLocation.orEmpty()
    )
}