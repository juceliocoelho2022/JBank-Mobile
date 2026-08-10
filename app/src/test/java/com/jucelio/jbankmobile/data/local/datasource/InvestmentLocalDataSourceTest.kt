package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.InvestmentDao
import com.jucelio.jbankmobile.data.local.mapper.InvestmentLocalMapper
import com.jucelio.jbankmobile.fixtures.portfolio.InvestmentFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class InvestmentLocalDataSourceTest {

    private lateinit var dao: InvestmentDao
    private lateinit var dataSource: InvestmentLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = InvestmentLocalDataSource(dao)
    }

    @Test
    fun `observeInvestments deve retornar fluxo fornecido pelo dao`() = runTest {
        val investments = InvestmentFixtures.investmentList()
            .map(InvestmentLocalMapper::toEntity)

        every {
            dao.observeInvestments()
        } returns flowOf(investments)

        val result = dataSource.observeInvestments().first()

        assertEquals(investments, result)

        verify(exactly = 1) {
            dao.observeInvestments()
        }
    }

    @Test
    fun `getInvestments deve retornar investimentos fornecidos pelo dao`() = runTest {
        val investments = InvestmentFixtures.investmentList()
            .map(InvestmentLocalMapper::toEntity)

        coEvery {
            dao.getInvestments()
        } returns investments

        val result = dataSource.getInvestments()

        assertEquals(investments, result)

        coVerify(exactly = 1) {
            dao.getInvestments()
        }
    }

    @Test
    fun `getInvestmentById deve retornar investimento correspondente`() = runTest {
        val investmentId = 1L
        val investment = InvestmentLocalMapper.toEntity(
            InvestmentFixtures.investmentWithId(investmentId)
        )

        coEvery {
            dao.getInvestmentById(investmentId)
        } returns investment

        val result = dataSource.getInvestmentById(investmentId)

        assertEquals(investment, result)

        coVerify(exactly = 1) {
            dao.getInvestmentById(investmentId)
        }
    }

    @Test
    fun `getInvestmentById deve retornar nulo quando investimento nao existir`() = runTest {
        val investmentId = 999L

        coEvery {
            dao.getInvestmentById(investmentId)
        } returns null

        val result = dataSource.getInvestmentById(investmentId)

        assertNull(result)

        coVerify(exactly = 1) {
            dao.getInvestmentById(investmentId)
        }
    }

    @Test
    fun `save deve inserir investimento no dao`() = runTest {
        val investment = InvestmentLocalMapper.toEntity(
            InvestmentFixtures.investment()
        )

        coEvery {
            dao.insertInvestment(investment)
        } returns Unit

        dataSource.save(investment)

        coVerify(exactly = 1) {
            dao.insertInvestment(investment)
        }
    }

    @Test
    fun `saveAll deve substituir investimentos no dao`() = runTest {
        val investments = InvestmentFixtures.investmentList()
            .map(InvestmentLocalMapper::toEntity)

        coEvery {
            dao.replaceInvestments(investments)
        } returns Unit

        dataSource.saveAll(investments)

        coVerify(exactly = 1) {
            dao.replaceInvestments(investments)
        }
    }

    @Test
    fun `clear deve limpar investimentos no dao`() = runTest {
        coEvery {
            dao.clearInvestments()
        } returns Unit

        dataSource.clear()

        coVerify(exactly = 1) {
            dao.clearInvestments()
        }
    }

    @Test(expected = RuntimeException::class)
    fun `getInvestments deve propagar excecao do dao`() = runTest {
        coEvery {
            dao.getInvestments()
        } throws RuntimeException("Erro no banco local")

        dataSource.getInvestments()
    }
}
