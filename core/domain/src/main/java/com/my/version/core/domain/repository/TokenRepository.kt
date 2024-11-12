package com.my.version.core.domain.repository

interface TokenRepository {
    fun getTokenFromPreference(): String?
}