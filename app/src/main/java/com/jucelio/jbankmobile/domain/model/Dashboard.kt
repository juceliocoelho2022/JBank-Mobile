package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

/**
 * Representa os dados consolidados exibidos no dashboard.
 *
 * Este modelo pertence ao domínio e não depende de Retrofit,
 * Gson ou qualquer componente Android.
 */
data class Dashboard(
    val totalCustomers: Long,
    val totalAccounts: Long,
    val totalTransactions: Long,
    val totalBalance: BigDecimal,
    val totalMoved: BigDecimal,
    val latestTransactions: List<DashboardTransaction>
)

/**
 * Representa uma movimentação exibida no dashboard.
 */
data class DashboardTransaction(
    val id: Long?,
    val type: String?,
    val amount: BigDecimal,
    val description: String?,
    val sourceAccountId: Long?,
    val targetAccountId: Long?,
    val createdAt: String?
)