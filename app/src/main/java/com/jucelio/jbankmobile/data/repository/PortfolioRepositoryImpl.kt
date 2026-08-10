package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.model.InvestmentCategory
import com.jucelio.jbankmobile.domain.model.Portfolio
import com.jucelio.jbankmobile.domain.repository.PortfolioRepository
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A JBank API ainda não expõe um endpoint de investimentos, então
 * o portfólio é montado localmente até que o backend disponibilize
 * o recurso (ver Roadmap - Versão 1.2).
 */
@Singleton
class PortfolioRepositoryImpl @Inject constructor() : PortfolioRepository {

    override suspend fun getPortfolio(): AppResult<Portfolio> {
        val investments = listOf(
            Investment(
                id = 1L,
                name = "Tesouro Selic 2029",
                category = InvestmentCategory.TESOURO_DIRETO,
                investedAmount = BigDecimal("3200.00"),
                currentAmount = BigDecimal("3384.51")
            ),
            Investment(
                id = 2L,
                name = "CDB JBank 120% CDI",
                category = InvestmentCategory.RENDA_FIXA,
                investedAmount = BigDecimal("2500.00"),
                currentAmount = BigDecimal("2617.30")
            ),
            Investment(
                id = 3L,
                name = "Fundo Multimercado JBank",
                category = InvestmentCategory.FUNDOS,
                investedAmount = BigDecimal("1500.00"),
                currentAmount = BigDecimal("1462.80")
            ),
            Investment(
                id = 4L,
                name = "Ações PETR4",
                category = InvestmentCategory.ACOES,
                investedAmount = BigDecimal("900.00"),
                currentAmount = BigDecimal("1034.20")
            ),
            Investment(
                id = 5L,
                name = "Bitcoin",
                category = InvestmentCategory.CRIPTO,
                investedAmount = BigDecimal("400.00"),
                currentAmount = BigDecimal("356.15")
            )
        )

        val portfolio = Portfolio(
            totalInvested = investments.sumInvested(),
            currentBalance = investments.sumCurrent(),
            investments = investments
        )

        return AppResult.Success(
            data = portfolio
        )
    }

    private fun List<Investment>.sumInvested(): BigDecimal {
        return fold(BigDecimal.ZERO) { total, investment ->
            total.add(investment.investedAmount)
        }
    }

    private fun List<Investment>.sumCurrent(): BigDecimal {
        return fold(BigDecimal.ZERO) { total, investment ->
            total.add(investment.currentAmount)
        }
    }
}
