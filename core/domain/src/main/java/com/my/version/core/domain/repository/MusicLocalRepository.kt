package com.my.version.core.domain.repository

import android.net.Uri
import com.my.version.core.domain.entity.CoverAudioFile
import com.my.version.core.domain.entity.MusicAudioFile
import java.io.InputStream

interface MusicLocalRepository {
    suspend fun getMusicAudioList(): List<MusicAudioFile>
    suspend fun writeMusicAudio(fileName: String,inputStream: InputStream)

    suspend fun playAudio(uri: Uri)
    //suspend fun writeMusicAudioFile()
}