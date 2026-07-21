package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.fixtures.account.AccountFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AccountRemoteDataSourceTest {

    private lateinit var api: JBankApi
    private lateinit var dataSource: AccountRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = AccountRemoteDataSource(api)
    }

    @Test
    fun `getAccounts deve retornar lista da API`() = runTest {

        val expected = AccountFixtures.accountResponseDtoList()

        coEvery {
            api.getAccounts()
        } returns expected

        val result = dataSource.getAccounts()

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            api.getAccounts()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getAccounts deve propagar excecao`() = runTest {

        coEvery {
            api.getAccounts()
        } throws RuntimeException("Erro na API")

        dataSource.getAccounts()
    }
}