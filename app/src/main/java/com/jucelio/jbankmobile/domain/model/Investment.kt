package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

/**
 * Modelo de domínio de um ativo da carteira de investimentos.
 */
data class Investment(
    val id: Long,
    val name: String,
    val type: String,
    val investedAmount: BigDecimal,
    val currentValue: BigDecimal,
    val profitability: BigDecimal
)
