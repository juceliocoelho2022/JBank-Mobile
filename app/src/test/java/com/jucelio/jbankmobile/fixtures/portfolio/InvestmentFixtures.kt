package com.jucelio.jbankmobile.fixtures.portfolio

import com.jucelio.jbankmobile.data.remote.dto.InvestmentResponseDto
import com.jucelio.jbankmobile.domain.model.Investment
import java.math.BigDecimal

object InvestmentFixtures {

    fun investment() = Investment(
        id = 1L,
        name = "Tesouro Selic 2029",
        type = "TESOURO_DIRETO",
        investedAmount = BigDecimal("1000.00"),
        currentValue = BigDecimal("1120.50"),
        profitability = BigDecimal("12.05")
    )

    fun investmentResponseDto() = InvestmentResponseDto(
        id = 1L,
        name = "Tesouro Selic 2029",
        type = "TESOURO_DIRETO",
        investedAmount = BigDecimal("1000.00"),
        currentValue = BigDecimal("1120.50"),
        profitability = BigDecimal("12.05")
    )

    fun stockInvestment() = Investment(
        id = 2L,
        name = "PETR4",
        type = "ACOES",
        investedAmount = BigDecimal("500.00"),
        currentValue = BigDecimal("430.00"),
        profitability = BigDecimal("-14.00")
    )

    fun fundInvestment() = Investment(
        id = 3L,
        name = "Fundo Multimercado XP",
        type = "FUNDOS",
        investedAmount = BigDecimal("2000.00"),
        currentValue = BigDecimal("2000.00"),
        profitability = BigDecimal.ZERO
    )

    fun investmentList() = listOf(
        investment(),
        stockInvestment(),
        fundInvestment()
    )

    fun investmentResponseDtoList() = listOf(
        investmentResponseDto(),
        InvestmentResponseDto(
            id = 2L,
            name = "PETR4",
            type = "ACOES",
            investedAmount = BigDecimal("500.00"),
            currentValue = BigDecimal("430.00"),
            profitability = BigDecimal("-14.00")
        ),
        InvestmentResponseDto(
            id = 3L,
            name = "Fundo Multimercado XP",
            type = "FUNDOS",
            investedAmount = BigDecimal("2000.00"),
            currentValue = BigDecimal("2000.00"),
            profitability = BigDecimal.ZERO
        )
    )

    fun emptyInvestmentList(): List<Investment> = emptyList()

    fun emptyInvestmentResponseDtoList(): List<InvestmentResponseDto> = emptyList()

    fun investmentWithId(
        id: Long
    ) = investment().copy(
        id = id
    )
}
