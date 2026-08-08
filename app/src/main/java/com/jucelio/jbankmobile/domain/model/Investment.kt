package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

data class Investment(
    val id: Long,
    val name: String,
    val investedAmount: BigDecimal,
    val profitability: Double
)