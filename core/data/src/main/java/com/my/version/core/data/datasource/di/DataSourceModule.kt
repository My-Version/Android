package com.my.version.core.data.datasource.di

import com.my.version.core.data.datasource.local.RecordDataSource
import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.datasource.remote.AuthDataSource
import com.my.version.core.data.datasource.remote.CoverDataSource
import com.my.version.core.data.datasource.remote.EvaluationDataSource
import com.my.version.core.data.datasource.remote.LyricDataSource
import com.my.version.core.data.datasource.remote.MusicDataSource
import com.my.version.core.data.datasourceimpl.local.RecordDataSourceImpl
import com.my.version.core.data.datasourceimpl.local.ScopedStorageDataSourceImpl
import com.my.version.core.data.datasourceimpl.remote.AuthDataSourceImpl
import com.my.version.core.data.datasourceimpl.remote.CoverDataSourceImpl
import com.my.version.core.data.datasourceimpl.remote.EvaluationDataSourceImpl
import com.my.version.core.data.datasourceimpl.remote.LyricDataSourceImpl
import com.my.version.core.data.datasourceimpl.remote.MusicDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCoverLocalDataSource(
        scopedStorageDataSourceImpl: ScopedStorageDataSourceImpl
    ): ScopedStorageDataSource

    @Binds
    @Singleton
    abstract fun bindRecordDataSource(
        recordDataSourceImpl: RecordDataSourceImpl
    ): RecordDataSource

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindMusicDataSource(
        musicDataSourceImpl: MusicDataSourceImpl
    ): MusicDataSource

    @Binds
    @Singleton
    abstract fun bindCoverDataSource(
        coverDataSourceImpl: CoverDataSourceImpl
    ): CoverDataSource

    @Binds
    @Singleton
    abstract fun bindEvaluationDataSource(
        evaluationDataSourceImpl: EvaluationDataSourceImpl
    ): EvaluationDataSource


    @Binds
    @Singleton
    abstract fun bindLyricDataSource(
        lyricDataSourceImpl: LyricDataSourceImpl
    ): LyricDataSource
}