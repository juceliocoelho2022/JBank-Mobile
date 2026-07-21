package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class DashboardLocalMapperTest {

    @Test
    fun `toDashboardEntity deve converter corretamente`() {

        val dto = DashboardFixtures.dashboardResponseDto()

        val entity = DashboardLocalMapper.toDashboardEntity(dto)

        Assert.assertEquals(dto.totalCustomers, entity.totalCustomers)
        Assert.assertEquals(dto.totalAccounts, entity.totalAccounts)
        Assert.assertEquals(dto.totalTransactions, entity.totalTransactions)
        Assert.assertEquals(dto.totalBalance, entity.totalBalance)
        Assert.assertEquals(dto.totalMoved, entity.totalMoved)
    }

    @Test
    fun `toDashboardEntity deve usar zero quando valores monetarios forem nulos`() {

        val dto = DashboardFixtures.dashboardResponseDto().copy(
            totalBalance = null,
            totalMoved = null
        )

        val entity = DashboardLocalMapper.toDashboardEntity(dto)

        Assert.assertEquals(BigDecimal.ZERO, entity.totalBalance)
        Assert.assertEquals(BigDecimal.ZERO, entity.totalMoved)
    }

    @Test
    fun `toTransactionEntities deve converter lista corretamente`() {

        val dto = DashboardFixtures.dashboardResponseDto()

        val list = DashboardLocalMapper.toTransactionEntities(dto)

        Assert.assertEquals(dto.latestTransactions.size, list.size)

        val firstDto = dto.latestTransactions.first()
        val firstEntity = list.first()

        Assert.assertEquals(firstDto.id, firstEntity.remoteId)
        Assert.assertEquals(firstDto.type, firstEntity.type)
        Assert.assertEquals(firstDto.amount, firstEntity.amount)
        Assert.assertEquals(firstDto.description, firstEntity.description)
    }

    @Test
    fun `toTransactionEntities deve manter lista vazia`() {

        val dto = DashboardFixtures.emptyDashboardResponseDto()

        val result = DashboardLocalMapper.toTransactionEntities(dto)

        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `toDomain deve converter DashboardEntity para Dashboard`() {

        val dto = DashboardFixtures.dashboardResponseDto()

        val entity =
            DashboardLocalMapper.toDashboardEntity(dto)

        val transactions =
            DashboardLocalMapper.toTransactionEntities(dto)

        val dashboard =
            DashboardLocalMapper.toDomain(
                entity,
                transactions
            )

        Assert.assertEquals(dto.totalCustomers, dashboard.totalCustomers)
        Assert.assertEquals(dto.totalAccounts, dashboard.totalAccounts)
        Assert.assertEquals(dto.totalTransactions, dashboard.totalTransactions)
        Assert.assertEquals(dto.totalBalance, dashboard.totalBalance)
        Assert.assertEquals(dto.totalMoved, dashboard.totalMoved)
        Assert.assertEquals(
            dto.latestTransactions.size,
            dashboard.latestTransactions.size
        )
    }
}