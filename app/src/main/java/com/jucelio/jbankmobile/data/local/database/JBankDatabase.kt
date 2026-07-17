package com.jucelio.jbankmobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jucelio.jbankmobile.data.local.converter.BigDecimalConverter
import com.jucelio.jbankmobile.data.local.dao.AccountDao
import com.jucelio.jbankmobile.data.local.dao.NotificationDao
import com.jucelio.jbankmobile.data.local.dao.TransactionDao
import com.jucelio.jbankmobile.data.local.entity.AccountEntity
import com.jucelio.jbankmobile.data.local.entity.NotificationEntity
import com.jucelio.jbankmobile.data.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(BigDecimalConverter::class)
abstract class JBankDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao

    abstract fun notificationDao(): NotificationDao

    companion object {
        const val DATABASE_NAME = "jbank_database"
    }
}