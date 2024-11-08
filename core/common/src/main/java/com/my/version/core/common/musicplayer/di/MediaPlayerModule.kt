package com.my.version.core.common.musicplayer.di

import android.content.Context
import com.my.version.core.common.musicplayer.StreamMediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer(
        @ApplicationContext context: Context
    ): StreamMediaPlayer = StreamMediaPlayer(context)

}