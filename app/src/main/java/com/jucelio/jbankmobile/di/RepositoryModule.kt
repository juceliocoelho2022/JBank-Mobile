package com.jucelio.jbankmobile.di

import com.jucelio.jbankmobile.data.repository.AccountRepositoryImpl
import com.jucelio.jbankmobile.data.repository.AuthRepositoryImpl
import com.jucelio.jbankmobile.data.repository.DashboardRepositoryImpl
import com.jucelio.jbankmobile.data.repository.TransactionRepositoryImpl
import com.jucelio.jbankmobile.domain.repository.AccountRepository
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import com.jucelio.jbankmobile.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.jucelio.jbankmobile.data.repository.NotificationRepositoryImpl
import com.jucelio.jbankmobile.domain.repository.NotificationRepository
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

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        implementation: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        implementation: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        implementation: NotificationRepositoryImpl
    ): NotificationRepository
}