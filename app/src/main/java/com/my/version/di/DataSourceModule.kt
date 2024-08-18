package com.my.version.di

import com.my.version.core.data.datasource.local.ScopedStorageDataSource
import com.my.version.core.data.datasourceimpl.local.ScopedStorageDataSourceImpl
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

}