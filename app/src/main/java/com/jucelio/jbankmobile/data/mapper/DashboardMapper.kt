package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.model.DashboardTransaction
import java.math.BigDecimal

/**
 * Converte o DTO recebido pela API para o modelo do domínio.
 */
fun DashboardResponseDto.toDomain(): Dashboard {
    return Dashboard(
        totalCustomers = totalCustomers,
        totalAccounts = totalAccounts,
        totalTransactions = totalTransactions,
        totalBalance = totalBalance ?: BigDecimal.ZERO,
        totalMoved = totalMoved ?: BigDecimal.ZERO,
        latestTransactions = latestTransactions.map(
            TransactionResponseDto::toDashboardDomain
        )
    )
}

private fun TransactionResponseDto.toDashboardDomain():
        DashboardTransaction {

    return DashboardTransaction(
        id = id,
        type = type,
        amount = amount ?: BigDecimal.ZERO,
        description = description,
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        createdAt = createdAt
    )
}