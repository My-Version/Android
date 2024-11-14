package com.my.version.core.data.service.di

import com.my.version.core.data.service.AuthService
import com.my.version.core.data.service.CoverService
import com.my.version.core.data.service.EvaluationService
import com.my.version.core.data.service.LyricService
import com.my.version.core.data.service.MusicService
import com.my.version.core.data.service.di.qualifier.JWT
import com.my.version.core.data.service.di.qualifier.LYRIC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(@JWT retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(@JWT retrofit: Retrofit): MusicService =
        retrofit.create(MusicService::class.java)

    @Provides
    @Singleton
    fun provideCoverService(@JWT retrofit: Retrofit): CoverService =
        retrofit.create(CoverService::class.java)

    @Provides
    @Singleton
    fun provideEvaluationService(@JWT retrofit: Retrofit): EvaluationService =
        retrofit.create(EvaluationService::class.java)

    @Provides
    @Singleton
    fun provideLyricService(@LYRIC retrofit: Retrofit): LyricService =
        retrofit.create(LyricService::class.java)
}