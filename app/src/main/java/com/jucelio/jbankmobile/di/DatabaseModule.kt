package com.jucelio.jbankmobile.di

import android.content.Context
import androidx.room.Room
import com.jucelio.jbankmobile.data.local.dao.AccountDao
import com.jucelio.jbankmobile.data.local.dao.DashboardDao
import com.jucelio.jbankmobile.data.local.dao.NotificationDao
import com.jucelio.jbankmobile.data.local.dao.TransactionDao
import com.jucelio.jbankmobile.data.local.database.JBankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJBankDatabase(
        @ApplicationContext context: Context
    ): JBankDatabase {

        return Room.databaseBuilder(
            context,
            JBankDatabase::class.java,
            JBankDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(
        database: JBankDatabase
    ): AccountDao =
        database.accountDao()

    @Provides
    @Singleton
    fun provideTransactionDao(
        database: JBankDatabase
    ): TransactionDao =
        database.transactionDao()

    @Provides
    @Singleton
    fun provideNotificationDao(
        database: JBankDatabase
    ): NotificationDao =
        database.notificationDao()

    @Provides
    @Singleton
    fun provideDashboardDao(
        database: JBankDatabase
    ): DashboardDao =
        database.dashboardDao()
}