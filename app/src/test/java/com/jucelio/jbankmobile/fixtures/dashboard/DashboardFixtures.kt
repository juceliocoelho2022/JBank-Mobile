package com.jucelio.jbankmobile.fixtures.dashboard

import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.model.DashboardTransaction
import java.math.BigDecimal

object DashboardFixtures {

    fun dashboard() = Dashboard(
        totalCustomers = 150,
        totalAccounts = 180,
        totalTransactions = 320,
        totalBalance = BigDecimal("250000.00"),
        totalMoved = BigDecimal("87500.50"),
        latestTransactions = transactionList()
    )

    fun dashboardResponseDto() = DashboardResponseDto(
        totalCustomers = 150,
        totalAccounts = 180,
        totalTransactions = 320,
        totalBalance = BigDecimal("250000.00"),
        totalMoved = BigDecimal("87500.50"),
        latestTransactions = transactionResponseDtoList()
    )

    fun dashboardTransaction() = DashboardTransaction(
        id = 1L,
        type = "PIX",
        amount = BigDecimal("250.00"),
        description = "Transferência PIX",
        sourceAccountId = 1L,
        targetAccountId = 2L,
        createdAt = "2026-07-20T09:30:00"
    )

    fun transactionResponseDto() = TransactionResponseDto(
        id = 1L,
        type = "PIX",
        amount = BigDecimal("250.00"),
        description = "Transferência PIX",
        sourceAccountId = 1L,
        targetAccountId = 2L,
        createdAt = "2026-07-20T09:30:00"
    )

    fun transactionList() = listOf(
        dashboardTransaction()
    )

    fun transactionResponseDtoList() = listOf(
        transactionResponseDto()
    )

    fun emptyDashboard() = Dashboard(
        totalCustomers = 0,
        totalAccounts = 0,
        totalTransactions = 0,
        totalBalance = BigDecimal.ZERO,
        totalMoved = BigDecimal.ZERO,
        latestTransactions = emptyList()
    )

    fun emptyDashboardResponseDto() = DashboardResponseDto(
        totalCustomers = 0,
        totalAccounts = 0,
        totalTransactions = 0,
        totalBalance = BigDecimal.ZERO,
        totalMoved = BigDecimal.ZERO,
        latestTransactions = emptyList()
    )

    fun dashboardList() = listOf(
        dashboard()
    )

    fun dashboardResponseDtoList() = listOf(
        dashboardResponseDto()
    )

    fun dashboardWithManyTransactions(
        quantity: Int = 20
    ): Dashboard {

        val transactions = (1..quantity).map {

            DashboardTransaction(
                id = it.toLong(),
                type = "PIX",
                amount = BigDecimal("100.00"),
                description = "PIX $it",
                sourceAccountId = 1L,
                targetAccountId = 2L,
                createdAt = "2026-07-20T10:00:00"
            )
        }

        return Dashboard(
            totalCustomers = 500,
            totalAccounts = 650,
            totalTransactions = quantity.toLong(),
            totalBalance = BigDecimal("1000000.00"),
            totalMoved = BigDecimal("450000.00"),
            latestTransactions = transactions
        )
    }

    fun dashboardResponseDtoWithManyTransactions(
        quantity: Int = 20
    ): DashboardResponseDto {

        val transactions = (1..quantity).map {

            TransactionResponseDto(
                id = it.toLong(),
                type = "PIX",
                amount = BigDecimal("100.00"),
                description = "PIX $it",
                sourceAccountId = 1L,
                targetAccountId = 2L,
                createdAt = "2026-07-20T10:00:00"
            )
        }

        return DashboardResponseDto(
            totalCustomers = 500,
            totalAccounts = 650,
            totalTransactions = quantity.toLong(),
            totalBalance = BigDecimal("1000000.00"),
            totalMoved = BigDecimal("450000.00"),
            latestTransactions = transactions
        )
    }
}