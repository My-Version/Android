package com.my.version.core.data.mapper

import com.my.version.core.domain.entity.CoverAudioFile
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun File.toCoverAudioFile(): CoverAudioFile? {
    try {
        val format = SimpleDateFormat("yyMMdd", Locale.getDefault())
        val fileInformation = this.name.split(".")[0].split("_")

        val date = format.parse(fileInformation[1])

        val outputFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
        val title = fileInformation[0]
        val createdDate = date?.let { outputFormat.format(it) } ?: ""

        Timber.tag("LocalFile").d("title: $title, createdDate: $createdDate")
        return CoverAudioFile(
            title = title,
            createdDate = createdDate,
            audio = this
        )
    } catch (e: IndexOutOfBoundsException) {
        return null
    }
}
