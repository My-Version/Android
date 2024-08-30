package com.my.version.core.common.media.di

import android.content.Context
import com.my.version.core.common.media.MyVersionMediaRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaModule {
    @Provides
    @Singleton
    fun provideMyVersionMediaRecorder(
        @ApplicationContext context: Context
    ): MyVersionMediaRecorder {
        return MyVersionMediaRecorder(context)
    }
}