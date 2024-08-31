package com.my.version.core.domain.entity

import java.io.File

data class CoverAudioFile(
    val title: String = "",
    val createdDate: String = "",
    val audio: File? = null
)
