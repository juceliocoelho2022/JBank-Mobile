package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Representa o portfólio de investimentos consolidado do usuário.
 *
 * Este modelo pertence ao domínio e não depende de Retrofit,
 * Gson ou qualquer componente Android.
 */
data class Portfolio(
    val totalInvested: BigDecimal,
    val currentBalance: BigDecimal,
    val investments: List<Investment>
) {

    val profit: BigDecimal
        get() = currentBalance.subtract(totalInvested)

    val profitPercentage: BigDecimal
        get() = totalInvested.percentageChangeTo(currentBalance)
}

/**
 * Representa um ativo individual dentro do portfólio.
 */
data class Investment(
    val id: Long,
    val name: String,
    val category: InvestmentCategory,
    val investedAmount: BigDecimal,
    val currentAmount: BigDecimal
) {

    val profit: BigDecimal
        get() = currentAmount.subtract(investedAmount)

    val profitPercentage: BigDecimal
        get() = investedAmount.percentageChangeTo(currentAmount)
}

enum class InvestmentCategory {
    RENDA_FIXA,
    TESOURO_DIRETO,
    FUNDOS,
    ACOES,
    CRIPTO
}

private fun BigDecimal.percentageChangeTo(
    finalValue: BigDecimal
): BigDecimal {
    if (this.signum() == 0) {
        return BigDecimal.ZERO
    }

    return finalValue
        .subtract(this)
        .divide(this, 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal(100))
}
