package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

data class Transaction(
    val id: Long,
    val description: String,
    val amount: BigDecimal,
    val date: String,
    val type: TransactionType
)

enum class TransactionType {
    DEPOSIT,
    WITHDRAW,
    PIX,
    TRANSFER,
    PAYMENT
}