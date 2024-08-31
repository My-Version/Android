package com.my.version.core.domain.entity

import java.io.File

data class MusicAudioFile(
    val title: String = "",
    val artist: String = "",
    val audio: File? = null
)
