package com.my.version.core.data.local.di

import android.content.Context
import com.my.version.core.data.local.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferenceModule {

    @Provides
    @Singleton
    fun providesPreference(
        @ApplicationContext context: Context
    ): PreferenceUtil = PreferenceUtil(context)
}