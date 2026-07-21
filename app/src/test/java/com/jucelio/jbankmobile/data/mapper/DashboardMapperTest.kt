package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DashboardMapperTest {

    @Test
    fun `toDomain deve converter DashboardResponseDto corretamente`() {

        // Arrange
        val dto = DashboardFixtures.dashboardResponseDto()

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(dto.totalCustomers, result.totalCustomers)
        assertEquals(dto.totalAccounts, result.totalAccounts)
        assertEquals(dto.totalTransactions, result.totalTransactions)
        assertEquals(dto.totalBalance, result.totalBalance)
        assertEquals(dto.totalMoved, result.totalMoved)
        assertEquals(
            dto.latestTransactions.size,
            result.latestTransactions.size
        )

        val dtoTransaction = dto.latestTransactions.first()
        val domainTransaction = result.latestTransactions.first()

        assertEquals(dtoTransaction.id, domainTransaction.id)
        assertEquals(dtoTransaction.type, domainTransaction.type)
        assertEquals(dtoTransaction.amount, domainTransaction.amount)
        assertEquals(dtoTransaction.description, domainTransaction.description)
        assertEquals(dtoTransaction.sourceAccountId, domainTransaction.sourceAccountId)
        assertEquals(dtoTransaction.targetAccountId, domainTransaction.targetAccountId)
        assertEquals(dtoTransaction.createdAt, domainTransaction.createdAt)
    }

    @Test
    fun `toDomain deve converter valores nulos para BigDecimal ZERO`() {

        // Arrange
        val dto = DashboardFixtures.dashboardResponseDto().copy(
            totalBalance = null,
            totalMoved = null
        )

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(BigDecimal.ZERO, result.totalBalance)
        assertEquals(BigDecimal.ZERO, result.totalMoved)
    }

    @Test
    fun `toDomain deve converter lista vazia`() {

        // Arrange
        val dto = DashboardFixtures.emptyDashboardResponseDto()

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(0, result.latestTransactions.size)
    }

    @Test
    fun `toDomain deve converter varias transacoes`() {

        // Arrange
        val dto =
            DashboardFixtures.dashboardResponseDtoWithManyTransactions(20)

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(20, result.latestTransactions.size)
    }
}