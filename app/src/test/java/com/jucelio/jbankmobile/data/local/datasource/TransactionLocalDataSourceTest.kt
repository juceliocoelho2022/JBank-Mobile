package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.TransactionDao
import com.jucelio.jbankmobile.data.local.mapper.TransactionLocalMapper
import com.jucelio.jbankmobile.fixtures.transaction.TransactionFixtures
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionLocalDataSourceTest {

    private lateinit var dao: TransactionDao
    private lateinit var dataSource: TransactionLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = TransactionLocalDataSource(dao)
    }

    @Test
    fun `observeTransactions deve retornar Flow do DAO`() = runTest {

        val transactions = TransactionFixtures.transactionList()
            .map(TransactionLocalMapper::toEntity)

        every {
            dao.observeTransactions()
        } returns flowOf(transactions)

        val result = dataSource.observeTransactions().first()

        assertEquals(transactions, result)

        verify(exactly = 1) {
            dao.observeTransactions()
        }
    }

    @Test
    fun `getTransactions deve retornar lista do DAO`() = runTest {

        val transactions = TransactionFixtures.transactionList()
            .map(TransactionLocalMapper::toEntity)

        coEvery {
            dao.getTransactions()
        } returns transactions

        val result = dataSource.getTransactions()

        assertEquals(transactions, result)

        coVerify(exactly = 1) {
            dao.getTransactions()
        }
    }

    @Test
    fun `save deve inserir transacao`() = runTest {

        val transaction =
            TransactionLocalMapper.toEntity(
                TransactionFixtures.transaction()
            )

        coEvery {
            dao.insertTransaction(transaction)
        } returns Unit

        dataSource.save(transaction)

        coVerify(exactly = 1) {
            dao.insertTransaction(transaction)
        }
    }

    @Test
    fun `replaceAll deve substituir transacoes`() = runTest {

        val transactions = TransactionFixtures.transactionList()
            .map(TransactionLocalMapper::toEntity)

        coEvery {
            dao.replaceTransactions(transactions)
        } returns Unit

        dataSource.replaceAll(transactions)

        coVerify(exactly = 1) {
            dao.replaceTransactions(transactions)
        }
    }

    @Test
    fun `clear deve limpar transacoes`() = runTest {

        coEvery {
            dao.clearTransactions()
        } returns Unit

        dataSource.clear()

        coVerify(exactly = 1) {
            dao.clearTransactions()
        }
    }
}