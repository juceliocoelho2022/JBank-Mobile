package com.jucelio.jbankmobile.fixtures.transaction

import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Transaction
import java.math.BigDecimal

object TransactionFixtures {

    fun transaction() = Transaction(
        id = 1L,
        type = "PIX",
        amount = BigDecimal("250.00"),
        description = "Pagamento via PIX",
        sourceAccountId = 10L,
        targetAccountId = 20L,
        createdAt = "2026-07-21T10:30:00"
    )

    fun transactionResponseDto() = TransactionResponseDto(
        id = 1L,
        type = "PIX",
        amount = BigDecimal("250.00"),
        description = "Pagamento via PIX",
        sourceAccountId = 10L,
        targetAccountId = 20L,
        createdAt = "2026-07-21T10:30:00"
    )

    fun depositTransaction() = Transaction(
        id = 2L,
        type = "DEPOSIT",
        amount = BigDecimal("1000.00"),
        description = "Depósito em conta",
        sourceAccountId = null,
        targetAccountId = 10L,
        createdAt = "2026-07-21T11:00:00"
    )

    fun withdrawalTransaction() = Transaction(
        id = 3L,
        type = "WITHDRAW",
        amount = BigDecimal("150.00"),
        description = "Saque realizado",
        sourceAccountId = 10L,
        targetAccountId = null,
        createdAt = "2026-07-21T12:00:00"
    )

    fun transactionList() = listOf(
        transaction(),
        depositTransaction(),
        withdrawalTransaction()
    )

    fun transactionResponseDtoList() = listOf(
        transactionResponseDto(),
        TransactionResponseDto(
            id = 2L,
            type = "DEPOSIT",
            amount = BigDecimal("1000.00"),
            description = "Depósito em conta",
            sourceAccountId = null,
            targetAccountId = 10L,
            createdAt = "2026-07-21T11:00:00"
        ),
        TransactionResponseDto(
            id = 3L,
            type = "WITHDRAW",
            amount = BigDecimal("150.00"),
            description = "Saque realizado",
            sourceAccountId = 10L,
            targetAccountId = null,
            createdAt = "2026-07-21T12:00:00"
        )
    )

    fun nullableTransactionResponseDto() = TransactionResponseDto(
        id = null,
        type = null,
        amount = null,
        description = null,
        sourceAccountId = null,
        targetAccountId = null,
        createdAt = null
    )

    fun emptyTransactionList(): List<Transaction> = emptyList()

    fun emptyTransactionResponseDtoList(): List<TransactionResponseDto> =
        emptyList()
}