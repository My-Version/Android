package com.my.version.core.data.service

import android.content.Context
import android.os.Environment
import com.my.version.core.domain.entity.CoverAudioFile
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Singleton

@Singleton
class LocalFileService(
    @ActivityContext private val context: Context
){
    private val externalStorage = context.getExternalFilesDirs(null).toString()


    /*fun getCoverAudioFileList(): List<CoverAudioFile> {

    }*/
}