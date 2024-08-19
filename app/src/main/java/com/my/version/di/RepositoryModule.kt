package com.my.version.di

import com.my.version.core.data.repositoryimpl.CoverLocalRepositoryImpl
import com.my.version.core.data.repositoryimpl.MusicLocalRepositoryImpl
import com.my.version.core.domain.repository.CoverLocalRepository
import com.my.version.core.domain.repository.MusicLocalRepository
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
    abstract fun bindCoverRepository(
        coverLocalRepositoryImpl: CoverLocalRepositoryImpl
    ): CoverLocalRepository

    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicLocalRepositoryImpl: MusicLocalRepositoryImpl
    ): MusicLocalRepository
}