package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.local.entity.TransactionEntity
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Transaction
import java.math.BigDecimal
import java.util.Objects

/**
 * O backend pode retornar transações sem [TransactionResponseDto.id]
 * (ex.: lançamentos pendentes). Como [TransactionEntity.id] é a
 * PrimaryKey usada com OnConflictStrategy.REPLACE, usar sempre o
 * mesmo fallback (0L) faz com que múltiplas transações sem id se
 * sobrescrevam mutuamente no Room. Por isso o fallback é derivado
 * do conteúdo do próprio registro.
 */
private fun TransactionResponseDto.fallbackId(): Long {
    return Objects.hash(
        type,
        amount,
        description,
        sourceAccountId,
        targetAccountId,
        createdAt
    ).toLong()
}

fun TransactionResponseDto.toDomain(): Transaction {
    return Transaction(
        id = id ?: fallbackId(),
        type = type.orEmpty(),
        amount = amount ?: BigDecimal.ZERO,
        description = description.orEmpty(),
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        createdAt = createdAt.orEmpty()
    )
}

fun TransactionResponseDto.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id ?: fallbackId(),
        type = type.orEmpty(),
        amount = amount ?: BigDecimal.ZERO,
        description = description.orEmpty(),
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        createdAt = createdAt.orEmpty()
    )
}