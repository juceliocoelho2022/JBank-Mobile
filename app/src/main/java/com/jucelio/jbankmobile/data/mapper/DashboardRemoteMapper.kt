package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.model.DashboardTransaction
import java.math.BigDecimal

/**
 * Responsável por converter os DTOs recebidos pela API
 * em modelos da camada de domínio.
 */
object DashboardRemoteMapper {

    fun toDomain(
        dto: DashboardResponseDto
    ): Dashboard {
        return Dashboard(
            totalCustomers = dto.totalCustomers,
            totalAccounts = dto.totalAccounts,
            totalTransactions = dto.totalTransactions,
            totalBalance = dto.totalBalance ?: BigDecimal.ZERO,
            totalMoved = dto.totalMoved ?: BigDecimal.ZERO,
            latestTransactions = dto.latestTransactions.map(
                ::toDashboardTransaction
            )
        )
    }

    private fun toDashboardTransaction(
        dto: TransactionResponseDto
    ): DashboardTransaction {
        return DashboardTransaction(
            id = dto.id,
            type = dto.type,
            amount = dto.amount ?: BigDecimal.ZERO,
            description = dto.description,
            sourceAccountId = dto.sourceAccountId,
            targetAccountId = dto.targetAccountId,
            createdAt = dto.createdAt
        )
    }
}