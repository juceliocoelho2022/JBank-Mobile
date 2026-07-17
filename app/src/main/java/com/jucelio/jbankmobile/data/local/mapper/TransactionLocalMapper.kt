package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.data.local.entity.TransactionEntity
import com.jucelio.jbankmobile.domain.model.Transaction

object TransactionLocalMapper {

    fun toEntity(transaction: Transaction): TransactionEntity {
        return TransactionEntity(
            id = transaction.id,
            type = transaction.type,
            amount = transaction.amount,
            description = transaction.description,
            sourceAccountId = transaction.sourceAccountId,
            targetAccountId = transaction.targetAccountId,
            createdAt = transaction.createdAt
        )
    }

    fun toDomain(entity: TransactionEntity): Transaction {
        return Transaction(
            id = entity.id,
            type = entity.type,
            amount = entity.amount,
            description = entity.description,
            sourceAccountId = entity.sourceAccountId,
            targetAccountId = entity.targetAccountId,
            createdAt = entity.createdAt
        )
    }
}