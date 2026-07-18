package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.TransactionDao
import com.jucelio.jbankmobile.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionLocalDataSource @Inject constructor(
    private val dao: TransactionDao
) {

    fun observeTransactions(): Flow<List<TransactionEntity>> =
        dao.observeTransactions()

    fun observeByAccount(
        accountId: Long
    ): Flow<List<TransactionEntity>> =
        dao.observeTransactionsByAccountId(accountId)

    suspend fun getTransactions(): List<TransactionEntity> =
        dao.getTransactions()

    suspend fun getByAccount(
        accountId: Long
    ): List<TransactionEntity> =
        dao.getTransactionsByAccountId(accountId)

    suspend fun save(
        transaction: TransactionEntity
    ) {
        dao.insertTransaction(transaction)
    }

    suspend fun saveAll(
        transactions: List<TransactionEntity>
    ) {
        dao.insertTransactions(transactions)
    }

    suspend fun replaceByAccount(
        accountId: Long,
        transactions: List<TransactionEntity>
    ) {
        dao.replaceTransactionsByAccountId(
            accountId = accountId,
            transactions = transactions
        )
    }

    suspend fun replaceAll(
        transactions: List<TransactionEntity>
    ) {
        dao.replaceTransactions(transactions)
    }

    suspend fun clear() {
        dao.clearTransactions()
    }
}