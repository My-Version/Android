package com.my.version.di

import android.content.Context
import com.my.version.core.data.service.ScopedStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalServiceModule {
    @Provides
    @Singleton
    fun provideLocalFileService(@ApplicationContext context: Context): ScopedStorageService {
        return ScopedStorageService(context)
    }
}