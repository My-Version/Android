package com.my.version.di

import com.my.version.core.data.repositoryimpl.AuthRepositoryImpl
import com.my.version.core.data.repositoryimpl.CoverRepositoryImpl
import com.my.version.core.data.repositoryimpl.CoverUploadRepositoryImpl
import com.my.version.core.data.repositoryimpl.EvaluationRepositoryImpl
import com.my.version.core.data.repositoryimpl.MusicRepositoryImpl
import com.my.version.core.data.repositoryimpl.RecordLocalRepositoryImpl
import com.my.version.core.data.repositoryimpl.RecordRepositoryImpl
import com.my.version.core.data.repositoryimpl.TokenRepositoryImpl
import com.my.version.core.domain.repository.AuthRepository
import com.my.version.core.domain.repository.CoverRepository
import com.my.version.core.domain.repository.CoverUploadRepository
import com.my.version.core.domain.repository.EvaluationRepository
import com.my.version.core.domain.repository.MusicRepository
import com.my.version.core.domain.repository.RecordLocalRepository
import com.my.version.core.domain.repository.RecordRepository
import com.my.version.core.domain.repository.TokenRepository
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
    abstract fun bindRecordLocalRepository(
        recordLocalRepositoryImpl: RecordLocalRepositoryImpl
    ): RecordLocalRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(
        recordRepositoryImpl: RecordRepositoryImpl
    ): RecordRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository

    @Binds
    @Singleton
    abstract fun bindCoverRepository(
        coverRepositoryImpl: CoverRepositoryImpl
    ): CoverRepository

    @Binds
    @Singleton
    abstract fun bindCoverUploadRepository(
        coverUploadRepositoryImpl: CoverUploadRepositoryImpl
    ): CoverUploadRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository


    @Binds
    @Singleton
    abstract fun bindEvaluationRepository(
        evaluationRepositoryImpl: EvaluationRepositoryImpl
    ): EvaluationRepository
}