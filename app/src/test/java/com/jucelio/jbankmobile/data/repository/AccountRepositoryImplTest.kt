package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.local.datasource.AccountLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.AccountLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.AccountRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.fixtures.account.AccountFixtures
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

class AccountRepositoryImplTest {

    private lateinit var remoteDataSource: AccountRemoteDataSource
    private lateinit var localDataSource: AccountLocalDataSource
    private lateinit var repository: AccountRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()

        repository = AccountRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `getAccounts deve salvar resposta remota no cache e retornar contas locais`() =
        runTest {
            val responseDtos =
                AccountFixtures.accountResponseDtoList()

            val entities =
                responseDtos.map { dto ->
                    dto.toEntity()
                }

            val expectedAccounts =
                entities.map { entity ->
                    AccountLocalMapper.toDomain(entity)
                }

            coEvery {
                remoteDataSource.getAccounts()
            } returns responseDtos

            coEvery {
                localDataSource.saveAll(entities)
            } returns Unit

            coEvery {
                localDataSource.getAccounts()
            } returns entities

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedAccounts,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.saveAll(entities)
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
            }

            confirmVerified(
                remoteDataSource,
                localDataSource
            )
        }

    @Test
    fun `getAccounts deve retornar cache quando API falhar e existirem contas locais`() =
        runTest {
            val cachedEntities =
                AccountFixtures.accountList()
                    .map(AccountLocalMapper::toEntity)

            val expectedAccounts =
                cachedEntities.map(AccountLocalMapper::toDomain)

            coEvery {
                remoteDataSource.getAccounts()
            } throws IOException("Sem conexão")

            coEvery {
                localDataSource.getAccounts()
            } returns cachedEntities

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedAccounts,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
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
    fun `getAccounts deve retornar falha quando API falhar e cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getAccounts()
            } throws IOException("Sem conexão")

            coEvery {
                localDataSource.getAccounts()
            } returns emptyList()

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Não foi possível conectar. Verifique sua internet.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
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
    fun `getAccounts deve retornar cache quando ocorrer timeout`() =
        runTest {
            val cachedEntities =
                AccountFixtures.accountList()
                    .map(AccountLocalMapper::toEntity)

            val expectedAccounts =
                cachedEntities.map(AccountLocalMapper::toDomain)

            coEvery {
                remoteDataSource.getAccounts()
            } throws SocketTimeoutException("Timeout")

            coEvery {
                localDataSource.getAccounts()
            } returns cachedEntities

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedAccounts,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }
        }

    @Test
    fun `getAccounts deve retornar falha de timeout quando cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getAccounts()
            } throws SocketTimeoutException("Timeout")

            coEvery {
                localDataSource.getAccounts()
            } returns emptyList()

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "A solicitação demorou mais que o esperado.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
            }

            coVerify(exactly = 0) {
                localDataSource.saveAll(any())
            }
        }

    @Test
    fun `getAccounts deve retornar mensagem da excecao quando cache estiver vazio`() =
        runTest {
            coEvery {
                remoteDataSource.getAccounts()
            } throws IllegalStateException("Erro inesperado na API")

            coEvery {
                localDataSource.getAccounts()
            } returns emptyList()

            val result = repository.getAccounts()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Erro inesperado na API",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getAccounts()
            }

            coVerify(exactly = 1) {
                localDataSource.getAccounts()
            }
        }

    @Test(expected = CancellationException::class)
    fun `getAccounts deve propagar cancelamento da coroutine`() =
        runTest {
            coEvery {
                remoteDataSource.getAccounts()
            } throws CancellationException("Operação cancelada")

            repository.getAccounts()
        }
}