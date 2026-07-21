package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.DashboardDao
import com.jucelio.jbankmobile.data.local.mapper.DashboardLocalMapper
import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DashboardLocalDataSourceTest {

    private lateinit var dao: DashboardDao
    private lateinit var dataSource: DashboardLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = DashboardLocalDataSource(dao)
    }

    @Test
    fun `saveDashboard deve salvar dashboard e transacoes`() = runTest {

        val dto = DashboardFixtures.dashboardResponseDto()

        coEvery {
            dao.replaceDashboard(any(), any())
        } returns Unit

        dataSource.saveDashboard(dto)

        coVerify(exactly = 1) {
            dao.replaceDashboard(
                DashboardLocalMapper.toDashboardEntity(dto),
                DashboardLocalMapper.toTransactionEntities(dto)
            )
        }
    }

    @Test
    fun `getDashboard deve retornar dashboard convertido`() = runTest {

        val dto = DashboardFixtures.dashboardResponseDto()

        val entity =
            DashboardLocalMapper.toDashboardEntity(dto)

        val transactions =
            DashboardLocalMapper.toTransactionEntities(dto)

        coEvery { dao.getDashboard() } returns entity
        coEvery { dao.getLatestTransactions() } returns transactions

        val result = dataSource.getDashboard()

        assertNotNull(result)
        assertEquals(dto.totalCustomers, result!!.totalCustomers)
        assertEquals(dto.latestTransactions.size, result.latestTransactions.size)
    }

    @Test
    fun `getDashboard deve retornar null quando nao existir`() = runTest {

        coEvery {
            dao.getDashboard()
        } returns null

        val result = dataSource.getDashboard()

        assertNull(result)
    }

    @Test
    fun `clear deve limpar dashboard`() = runTest {

        coEvery {
            dao.clearAll()
        } returns Unit

        dataSource.clear()

        coVerify(exactly = 1) {
            dao.clearAll()
        }
    }
}