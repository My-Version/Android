package com.my.version

import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.data.datasourceimpl.local.CoverLocalDataSourceImpl
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
        coverLocalDataSourceImpl: CoverLocalDataSourceImpl
    ): CoverLocalDataSource

}