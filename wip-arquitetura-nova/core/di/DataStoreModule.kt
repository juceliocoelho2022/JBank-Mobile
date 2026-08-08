package com.jucelio.jbankmobile.core.di

import android.content.Context
import com.jucelio.jbankmobile.core.security.JwtTokenProvider
import com.jucelio.jbankmobile.core.security.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTokenProvider(
        @ApplicationContext context: Context
    ): TokenProvider {
        return JwtTokenProvider(context)
    }
}