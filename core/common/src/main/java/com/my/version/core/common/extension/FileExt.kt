package com.my.version.core.common.extension

import android.media.MediaMetadataRetriever
import java.io.File

fun File.isAudioFile(): Boolean = runCatching {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this.path)
        val hasAudio = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)
        retriever.release()
        hasAudio != null // null이 아니면 오디오 트랙이 존재
    }.isSuccess
