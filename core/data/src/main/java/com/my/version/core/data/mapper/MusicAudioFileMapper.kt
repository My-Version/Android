package com.my.version.core.data.mapper

import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.entity.MusicAudioFile
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun File.toMusicAudioFile(): MusicAudioFile? {
    try {
        val fileInformation = this.name.split(".")[0].split("_")
        val title = fileInformation[0]
        val artist = fileInformation[1]

        return MusicAudioFile(
            title = title,
            artist = artist,
            audio = this
        )
    } catch (e: IndexOutOfBoundsException) {
        return null
    }
}
