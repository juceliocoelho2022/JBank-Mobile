package com.jucelio.jbankmobile.core.di

import com.jucelio.jbankmobile.data.remote.datasource.AuthRemoteDataSource
import com.jucelio.jbankmobile.data.repository.AuthRepositoryImpl
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        remote: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(remote)
    }
}