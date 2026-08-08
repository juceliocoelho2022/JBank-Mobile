package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.local.datasource.TransactionLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.TransactionLocalMapper
import com.jucelio.jbankmobile.data.mapper.toEntity
import com.jucelio.jbankmobile.data.remote.datasource.TransactionRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.fixtures.transaction.TransactionFixtures
import io.mockk.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class TransactionRepositoryImplTest {

    private lateinit var remoteDataSource: TransactionRemoteDataSource
    private lateinit var localDataSource: TransactionLocalDataSource
    private lateinit var repository: TransactionRepositoryImpl

    private val accountId = 10L

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()

        repository = TransactionRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun `getStatement deve salvar cache e retornar transacoes`() = runTest {

        val response =
            TransactionFixtures.transactionResponseDtoList()

        val entities =
            response.map { it.toEntity() }

        val expected =
            entities.map {
                TransactionLocalMapper.toDomain(it)
            }

        coEvery {
            remoteDataSource.getStatement(accountId)
        } returns response

        coEvery {
            localDataSource.replaceByAccount(
                accountId,
                entities
            )
        } returns Unit

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns entities

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Success)

        assertEquals(
            expected,
            (result as AppResult.Success).data
        )

        coVerify(exactly = 1) {
            remoteDataSource.getStatement(accountId)
        }

        coVerify(exactly = 1) {
            localDataSource.replaceByAccount(
                accountId,
                entities
            )
        }

        coVerify(exactly = 1) {
            localDataSource.getByAccount(accountId)
        }

        confirmVerified(
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun `deve retornar cache quando API falhar`() = runTest {

        val entities =
            TransactionFixtures.transactionResponseDtoList()
                .map { it.toEntity() }

        val expected =
            entities.map {
                TransactionLocalMapper.toDomain(it)
            }

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws IOException()

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns entities

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Success)

        assertEquals(
            expected,
            (result as AppResult.Success).data
        )
    }

    @Test
    fun `deve retornar failure quando cache estiver vazio`() = runTest {

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws IOException()

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns emptyList()

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Não foi possível conectar. Verifique sua internet.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar cache quando ocorrer timeout`() = runTest {

        val entities =
            TransactionFixtures.transactionResponseDtoList()
                .map { it.toEntity() }

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws SocketTimeoutException()

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns entities

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Success)
    }

    @Test
    fun `deve retornar timeout quando cache estiver vazio`() = runTest {

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws SocketTimeoutException()

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns emptyList()

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "A solicitação demorou mais que o esperado.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar erro desconhecido`() = runTest {

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws IllegalStateException("Erro inesperado")

        coEvery {
            localDataSource.getByAccount(accountId)
        } returns emptyList()

        val result =
            repository.getStatement(accountId)

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Erro inesperado",
            (result as AppResult.Failure).message
        )
    }

    @Test(expected = CancellationException::class)
    fun `deve propagar cancellation`() = runTest {

        coEvery {
            remoteDataSource.getStatement(accountId)
        } throws CancellationException()

        repository.getStatement(accountId)
    }
}