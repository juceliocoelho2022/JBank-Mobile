package com.jucelio.jbankmobile.domain.usecase.portfolio

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.model.InvestmentCategory
import com.jucelio.jbankmobile.domain.model.Portfolio
import com.jucelio.jbankmobile.domain.repository.PortfolioRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.math.BigDecimal
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPortfolioUseCaseTest {

    private lateinit var repository: PortfolioRepository
    private lateinit var useCase: GetPortfolioUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPortfolioUseCase(repository)
    }

    @Test
    fun `deve retornar o portfolio do usuario`() = runTest {

        val portfolio = Portfolio(
            totalInvested = BigDecimal("1000.00"),
            currentBalance = BigDecimal("1100.00"),
            investments = listOf(
                Investment(
                    id = 1L,
                    name = "Tesouro Selic 2029",
                    category = InvestmentCategory.TESOURO_DIRETO,
                    investedAmount = BigDecimal("1000.00"),
                    currentAmount = BigDecimal("1100.00")
                )
            )
        )

        coEvery {
            repository.getPortfolio()
        } returns AppResult.Success(portfolio)

        val result = useCase()

        assertTrue(result is AppResult.Success)
        assertEquals(portfolio, (result as AppResult.Success).data)

        coVerify(exactly = 1) {
            repository.getPortfolio()
        }
    }

    @Test
    fun `deve retornar falha quando o repositorio falhar`() = runTest {

        coEvery {
            repository.getPortfolio()
        } returns AppResult.Failure("Erro ao carregar portfólio")

        val result = useCase()

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Erro ao carregar portfólio",
            (result as AppResult.Failure).message
        )
    }
}
