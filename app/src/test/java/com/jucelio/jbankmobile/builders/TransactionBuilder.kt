package com.jucelio.jbankmobile.builders

import com.jucelio.jbankmobile.domain.model.Transaction
import java.math.BigDecimal

class TransactionBuilder {

    private var id: Long = 1L
    private var type: String = "PIX"
    private var amount: BigDecimal = BigDecimal("250.00")
    private var description: String = "Transferência PIX"
    private var sourceAccountId: Long? = 1L
    private var targetAccountId: Long? = 2L
    private var createdAt: String = "2026-07-20T09:30:00"

    fun withId(value: Long) = apply {
        id = value
    }

    fun withType(value: String) = apply {
        type = value
    }

    fun withAmount(value: BigDecimal) = apply {
        amount = value
    }

    fun withDescription(value: String) = apply {
        description = value
    }

    fun withSourceAccountId(value: Long?) = apply {
        sourceAccountId = value
    }

    fun withTargetAccountId(value: Long?) = apply {
        targetAccountId = value
    }

    fun withCreatedAt(value: String) = apply {
        createdAt = value
    }

    fun asDeposit() = apply {
        type = "DEPOSIT"
        description = "Depósito"
        sourceAccountId = null
    }

    fun asWithdraw() = apply {
        type = "WITHDRAW"
        description = "Saque"
        targetAccountId = null
    }

    fun asPix() = apply {
        type = "PIX"
        description = "Transferência PIX"
    }

    fun build(): Transaction {
        return Transaction(
            id = id,
            type = type,
            amount = amount,
            description = description,
            sourceAccountId = sourceAccountId,
            targetAccountId = targetAccountId,
            createdAt = createdAt
        )
    }
}