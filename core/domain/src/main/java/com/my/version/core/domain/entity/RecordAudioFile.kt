package com.my.version.core.domain.entity

import java.io.File

data class RecordAudioFile(
    val title: String = "",
    val createdDate: String = "",
    val time: String = "",
    val audio: File? = null
)
