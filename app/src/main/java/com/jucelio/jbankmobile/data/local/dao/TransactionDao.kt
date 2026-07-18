package com.jucelio.jbankmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jucelio.jbankmobile.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(
        """
        SELECT *
        FROM transactions
        ORDER BY createdAt DESC
        """
    )
    fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT *
        FROM transactions
        ORDER BY createdAt DESC
        """
    )
    suspend fun getTransactions(): List<TransactionEntity>

    @Query(
        """
        SELECT *
        FROM transactions
        WHERE id = :transactionId
        LIMIT 1
        """
    )
    suspend fun getTransactionById(
        transactionId: Long
    ): TransactionEntity?

    @Query(
        """
        SELECT *
        FROM transactions
        WHERE sourceAccountId = :accountId
           OR targetAccountId = :accountId
        ORDER BY createdAt DESC
        """
    )
    suspend fun getTransactionsByAccountId(
        accountId: Long
    ): List<TransactionEntity>

    @Query(
        """
        SELECT *
        FROM transactions
        WHERE sourceAccountId = :accountId
           OR targetAccountId = :accountId
        ORDER BY createdAt DESC
        """
    )
    fun observeTransactionsByAccountId(
        accountId: Long
    ): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(
        transaction: TransactionEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(
        transactions: List<TransactionEntity>
    )

    @Query(
        """
        DELETE FROM transactions
        WHERE id = :transactionId
        """
    )
    suspend fun deleteTransactionById(
        transactionId: Long
    )

    @Query(
        """
        DELETE FROM transactions
        WHERE sourceAccountId = :accountId
           OR targetAccountId = :accountId
        """
    )
    suspend fun deleteTransactionsByAccountId(
        accountId: Long
    )

    @Query(
        """
        DELETE FROM transactions
        """
    )
    suspend fun clearTransactions()

    @Transaction
    suspend fun replaceTransactionsByAccountId(
        accountId: Long,
        transactions: List<TransactionEntity>
    ) {
        deleteTransactionsByAccountId(accountId)

        if (transactions.isNotEmpty()) {
            insertTransactions(transactions)
        }
    }

    @Transaction
    suspend fun replaceTransactions(
        transactions: List<TransactionEntity>
    ) {
        clearTransactions()

        if (transactions.isNotEmpty()) {
            insertTransactions(transactions)
        }
    }
}