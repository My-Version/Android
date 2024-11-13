package com.my.version.core.data.repositoryimpl

import com.my.version.core.data.local.PreferenceUtil
import com.my.version.core.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val preferenceUtil: PreferenceUtil
) : TokenRepository {
    override fun getTokenFromPreference(): String? =
        preferenceUtil.idToken.takeIf { preferenceUtil.idToken.isNotBlank() }
}