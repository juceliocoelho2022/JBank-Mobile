package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DashboardRemoteDataSourceTest {

    private lateinit var api: JBankApi
    private lateinit var dataSource: DashboardRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = DashboardRemoteDataSource(api)
    }

    @Test
    fun `getDashboard deve retornar dados da API`() = runTest {

        val expected = DashboardFixtures.dashboardResponseDto()

        coEvery {
            api.getDashboard()
        } returns expected

        val result = dataSource.getDashboard()

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            api.getDashboard()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getDashboard deve propagar excecao`() = runTest {

        coEvery {
            api.getDashboard()
        } throws RuntimeException("Erro na API")

        dataSource.getDashboard()
    }
}