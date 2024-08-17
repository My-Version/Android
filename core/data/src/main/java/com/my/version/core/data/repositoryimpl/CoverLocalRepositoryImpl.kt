package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.datasource.local.CoverLocalDataSource
import com.my.version.core.domain.repository.CoverLocalRepository
import javax.inject.Inject

class CoverLocalRepositoryImpl @Inject constructor(
    coverLocalDataSource: CoverLocalDataSource
): CoverLocalRepository {

}