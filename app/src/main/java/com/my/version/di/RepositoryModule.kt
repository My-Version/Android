package com.my.version.di

import com.my.version.core.data.repositoryimpl.CoverLocalRepositoryImpl
import com.my.version.core.data.repositoryimpl.MusicLocalRepositoryImpl
import com.my.version.core.data.repositoryimpl.RecordLocalRepositoryImpl
import com.my.version.core.data.repositoryimpl.RecordRepositoryImpl
import com.my.version.core.domain.repository.CoverLocalRepository
import com.my.version.core.domain.repository.MusicLocalRepository
import com.my.version.core.domain.repository.RecordLocalRepository
import com.my.version.core.domain.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoverLocalRepository(
        coverLocalRepositoryImpl: CoverLocalRepositoryImpl
    ): CoverLocalRepository

    @Binds
    @Singleton
    abstract fun bindMusicLocalRepository(
        musicLocalRepositoryImpl: MusicLocalRepositoryImpl
    ): MusicLocalRepository

    @Binds
    @Singleton
    abstract fun bindRecordLocalRepository(
        recordLocalRepositoryImpl: RecordLocalRepositoryImpl
    ): RecordLocalRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(
        recordRepositoryImpl: RecordRepositoryImpl
    ): RecordRepository

}