package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Transaction
import java.math.BigDecimal

fun TransactionResponseDto.toDomain(): Transaction {
    return Transaction(
        id = id ?: 0L,
        type = type.orEmpty(),
        amount = amount ?: BigDecimal.ZERO,
        description = description.orEmpty(),
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        createdAt = createdAt.orEmpty()
    )
}