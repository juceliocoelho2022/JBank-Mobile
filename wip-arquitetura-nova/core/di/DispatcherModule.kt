package com.jucelio.jbankmobile.core.di

import com.jucelio.jbankmobile.core.utils.DefaultDispatcherProvider
import com.jucelio.jbankmobile.core.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider
}