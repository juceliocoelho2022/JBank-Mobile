package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.fixtures.transaction.TransactionFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionRemoteDataSourceTest {

    private lateinit var api: JBankApi
    private lateinit var dataSource: TransactionRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = TransactionRemoteDataSource(api)
    }

    @Test
    fun `getStatement deve retornar lista da API`() = runTest {

        val accountId = 1L

        val expected =
            TransactionFixtures.transactionResponseDtoList()

        coEvery {
            api.getStatement(accountId)
        } returns expected

        val result =
            dataSource.getStatement(accountId)

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            api.getStatement(accountId)
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getStatement deve propagar excecao`() = runTest {

        val accountId = 1L

        coEvery {
            api.getStatement(accountId)
        } throws RuntimeException("Erro na API")

        dataSource.getStatement(accountId)
    }
}