package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.local.datasource.InvestmentLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.InvestmentLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.PortfolioRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.fixtures.portfolio.InvestmentFixtures
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class PortfolioRepositoryImplTest {

    private lateinit var remoteDataSource: PortfolioRemoteDataSource
    private lateinit var localDataSource: InvestmentLocalDataSource
    private lateinit var repository: PortfolioRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()

        repository = PortfolioRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `getInvestments deve salvar resposta remota no cache e retornar investimentos locais`() =
        runTest {
            val responseDtos =
                InvestmentFixtures.investmentResponseDtoList()

            val entities =
                responseDtos.map { dto ->
                    dto.toEntity()
                }

            val expectedInvestments =
                entities.map { entity ->
                    InvestmentLocalMapper.toDomain(entity)
                }

            coEvery {
                remoteDataSource.getInvestments()
            } returns responseDtos

            coEvery {
                localDataSource.saveAll(entities)
            } returns Unit

            coEvery {
                localDataSource.getInvestments()
            } returns entities

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedInvestments,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.saveAll(entities)
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }

            confirmVerified(
                remoteDataSource,
                localDataSource
            )
        }

    @Test
    fun `getInvestments deve retornar cache quando API falhar e existirem investimentos locais`() =
        runTest {
            val cachedEntities =
                InvestmentFixtures.investmentList()
                    .map(InvestmentLocalMapper::toEntity)

            val expectedInvestments =
                cachedEntities.map(InvestmentLocalMapper::toDomain)

            coEvery {
                remoteDataSource.getInvestments()
            } throws IOException("Sem conexão")

            coEvery {
                localDataSource.getInvestments()
            } returns cachedEntities

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedInvestments,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }

            confirmVerified(
                remoteDataSource,
                localDataSource
            )
        }

    @Test
    fun `getInvestments deve retornar falha quando API falhar e cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getInvestments()
            } throws IOException("Sem conexão")

            coEvery {
                localDataSource.getInvestments()
            } returns emptyList()

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Não foi possível conectar. Verifique sua internet.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }

            confirmVerified(
                remoteDataSource,
                localDataSource
            )
        }

    @Test
    fun `getInvestments deve retornar cache quando ocorrer timeout`() =
        runTest {
            val cachedEntities =
                InvestmentFixtures.investmentList()
                    .map(InvestmentLocalMapper::toEntity)

            val expectedInvestments =
                cachedEntities.map(InvestmentLocalMapper::toDomain)

            coEvery {
                remoteDataSource.getInvestments()
            } throws SocketTimeoutException("Timeout")

            coEvery {
                localDataSource.getInvestments()
            } returns cachedEntities

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedInvestments,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }
        }

    @Test
    fun `getInvestments deve retornar falha de timeout quando cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getInvestments()
            } throws SocketTimeoutException("Timeout")

            coEvery {
                localDataSource.getInvestments()
            } returns emptyList()

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "A solicitação demorou mais que o esperado.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }
        }

    @Test
    fun `getInvestments deve retornar mensagem da excecao quando cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getInvestments()
            } throws IllegalStateException("Erro inesperado na API")

            coEvery {
                localDataSource.getInvestments()
            } returns emptyList()

            val result = repository.getInvestments()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Erro inesperado na API",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getInvestments()
            }

            coVerify(exactly = 1) {
                localDataSource.getInvestments()
            }
        }

    @Test(expected = CancellationException::class)
    fun `getInvestments deve propagar cancelamento da coroutine`() =
        runTest {
            coEvery {
                remoteDataSource.getInvestments()
            } throws CancellationException("Operação cancelada")

            repository.getInvestments()
        }
}
