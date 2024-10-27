package com.my.version.core.common.musicplayer.di

import android.content.Context
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MediaPlayerModule {

    @Binds
    @Singleton
    fun bindMediaPlayer(
        @ApplicationContext context: Context
    ): StreamMediaPlayer = StreamMediaPlayer(context)

}