package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

data class Transaction(
    val id: Long,
    val type: String,
    val amount: BigDecimal,
    val description: String,
    val sourceAccountId: Long?,
    val targetAccountId: Long?,
    val createdAt: String
)