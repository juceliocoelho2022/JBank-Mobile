package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.fixtures.portfolio.InvestmentFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PortfolioRemoteDataSourceTest {

    private lateinit var api: JBankApi
    private lateinit var dataSource: PortfolioRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = PortfolioRemoteDataSource(api)
    }

    @Test
    fun `getInvestments deve retornar lista da API`() = runTest {

        val expected = InvestmentFixtures.investmentResponseDtoList()

        coEvery {
            api.getPortfolio()
        } returns expected

        val result = dataSource.getInvestments()

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            api.getPortfolio()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getInvestments deve propagar excecao`() = runTest {

        coEvery {
            api.getPortfolio()
        } throws RuntimeException("Erro na API")

        dataSource.getInvestments()
    }
}
