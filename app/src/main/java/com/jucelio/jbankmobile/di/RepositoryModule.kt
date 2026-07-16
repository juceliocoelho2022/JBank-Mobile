package com.jucelio.jbankmobile.di

import com.jucelio.jbankmobile.data.repository.AuthRepositoryImpl
import com.jucelio.jbankmobile.data.repository.DashboardRepositoryImpl
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        implementation: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(
        implementation: DashboardRepositoryImpl
    ): DashboardRepository
}