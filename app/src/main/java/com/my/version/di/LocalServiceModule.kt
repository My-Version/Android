package com.my.version.di

import android.content.Context
import com.my.version.core.data.service.LocalFileService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalServiceModule {
    @Binds
    @Singleton
    fun provideLocalFileService(@ApplicationContext context: Context): LocalFileService {
        return LocalFileService(context)
    }
}