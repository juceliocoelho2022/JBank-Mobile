package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class DashboardRemoteMapperTest {

    @Test
    fun `toDomain deve converter DashboardResponseDto corretamente`() {
        // Arrange
        val dto = DashboardFixtures.dashboardResponseDto()

        // Act
        val result = DashboardRemoteMapper.toDomain(dto)

        // Assert
        assertEquals(dto.totalCustomers, result.totalCustomers)
        assertEquals(dto.totalAccounts, result.totalAccounts)
        assertEquals(dto.totalTransactions, result.totalTransactions)
        assertEquals(dto.totalBalance, result.totalBalance)
        assertEquals(dto.totalMoved, result.totalMoved)
        assertEquals(dto.latestTransactions.size, result.latestTransactions.size)

        val dtoTransaction = dto.latestTransactions.first()
        val resultTransaction = result.latestTransactions.first()

        assertEquals(dtoTransaction.id, resultTransaction.id)
        assertEquals(dtoTransaction.type, resultTransaction.type)
        assertEquals(dtoTransaction.amount, resultTransaction.amount)
        assertEquals(dtoTransaction.description, resultTransaction.description)
        assertEquals(dtoTransaction.sourceAccountId, resultTransaction.sourceAccountId)
        assertEquals(dtoTransaction.targetAccountId, resultTransaction.targetAccountId)
        assertEquals(dtoTransaction.createdAt, resultTransaction.createdAt)
    }

    @Test
    fun `toDomain deve usar zero quando totais monetarios forem nulos`() {
        // Arrange
        val dto = DashboardFixtures.dashboardResponseDto().copy(
            totalBalance = null,
            totalMoved = null
        )

        // Act
        val result = DashboardRemoteMapper.toDomain(dto)

        // Assert
        assertEquals(BigDecimal.ZERO, result.totalBalance)
        assertEquals(BigDecimal.ZERO, result.totalMoved)
    }

    @Test
    fun `toDomain deve usar zero quando valor da transacao for nulo`() {
        // Arrange
        val transaction = DashboardFixtures.transactionResponseDto().copy(
            amount = null
        )

        val dto = DashboardFixtures.dashboardResponseDto().copy(
            latestTransactions = listOf(transaction)
        )

        // Act
        val result = DashboardRemoteMapper.toDomain(dto)

        // Assert
        assertEquals(
            BigDecimal.ZERO,
            result.latestTransactions.first().amount
        )
    }

    @Test
    fun `toDomain deve manter lista vazia de transacoes`() {
        // Arrange
        val dto = DashboardFixtures.emptyDashboardResponseDto()

        // Act
        val result = DashboardRemoteMapper.toDomain(dto)

        // Assert
        assertTrue(result.latestTransactions.isEmpty())
    }

    @Test
    fun `toDomain deve converter varias transacoes`() {
        // Arrange
        val dto =
            DashboardFixtures.dashboardResponseDtoWithManyTransactions(
                quantity = 20
            )

        // Act
        val result = DashboardRemoteMapper.toDomain(dto)

        // Assert
        assertEquals(20, result.latestTransactions.size)
    }
}