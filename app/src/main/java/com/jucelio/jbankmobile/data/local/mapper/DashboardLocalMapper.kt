package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.data.local.entity.DashboardEntity
import com.jucelio.jbankmobile.data.local.entity.DashboardTransactionEntity
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.model.DashboardTransaction
import java.math.BigDecimal

object DashboardLocalMapper {

    fun toDashboardEntity(
        dto: DashboardResponseDto
    ): DashboardEntity {
        return DashboardEntity(
            id = 1,
            totalCustomers = dto.totalCustomers,
            totalAccounts = dto.totalAccounts,
            totalTransactions = dto.totalTransactions,
            totalBalance = dto.totalBalance ?: BigDecimal.ZERO,
            totalMoved = dto.totalMoved ?: BigDecimal.ZERO
        )
    }

    fun toTransactionEntities(
        dto: DashboardResponseDto
    ): List<DashboardTransactionEntity> {
        return dto.latestTransactions.mapIndexed { index, transaction ->

            DashboardTransactionEntity(
                position = index,
                remoteId = transaction.id,
                type = transaction.type,
                amount = transaction.amount ?: BigDecimal.ZERO,
                description = transaction.description,
                sourceAccountId = transaction.sourceAccountId,
                targetAccountId = transaction.targetAccountId,
                createdAt = transaction.createdAt
            )
        }
    }

    fun toDomain(
        dashboard: DashboardEntity,
        transactions: List<DashboardTransactionEntity>
    ): Dashboard {
        return Dashboard(
            totalCustomers = dashboard.totalCustomers,
            totalAccounts = dashboard.totalAccounts,
            totalTransactions = dashboard.totalTransactions,
            totalBalance = dashboard.totalBalance,
            totalMoved = dashboard.totalMoved,
            latestTransactions = transactions.map(::toDomainTransaction)
        )
    }

    private fun toDomainTransaction(
        entity: DashboardTransactionEntity
    ): DashboardTransaction {
        return DashboardTransaction(
            id = entity.remoteId,
            type = entity.type,
            amount = entity.amount,
            description = entity.description,
            sourceAccountId = entity.sourceAccountId,
            targetAccountId = entity.targetAccountId,
            createdAt = entity.createdAt
        )
    }
}