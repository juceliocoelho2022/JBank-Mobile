package com.jucelio.jbankmobile.data.remote.dto

import java.math.BigDecimal

data class TransactionResponseDto(
    val id: Long? = null,
    val type: String? = null,
    val amount: BigDecimal? = null,
    val description: String? = null,
    val sourceAccountId: Long? = null,
    val targetAccountId: Long? = null,
    val createdAt: String? = null
)

data class DashboardResponseDto(
    val totalCustomers: Long = 0,
    val totalAccounts: Long = 0,
    val totalTransactions: Long = 0,
    val totalBalance: BigDecimal? = BigDecimal.ZERO,
    val totalMoved: BigDecimal? = BigDecimal.ZERO,
    val latestTransactions: List<TransactionResponseDto> = emptyList()
)
