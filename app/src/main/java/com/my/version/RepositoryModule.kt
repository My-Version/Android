package com.my.version

import com.my.version.core.data.repositoryimpl.CoverLocalRepositoryImpl
import com.my.version.core.domain.repository.CoverLocalRepository
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
}