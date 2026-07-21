package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.NotificationRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures
import io.mockk.coEvery
import io.mockk.coVerify
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

class NotificationRepositoryImplTest {

    private lateinit var remoteDataSource: NotificationRemoteDataSource
    private lateinit var repository: NotificationRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()

        repository = NotificationRepositoryImpl(
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `getNotifications deve retornar sucesso com notificacoes convertidas`() =
        runTest {
            val responseDtos =
                NotificationFixtures.notificationResponseDtoList()

            val expectedNotifications =
                responseDtos.map { dto ->
                    dto.toDomain()
                }

            coEvery {
                remoteDataSource.getNotifications()
            } returns responseDtos

            val result = repository.getNotifications()

            assertTrue(result is AppResult.Success)

            val success = result as AppResult.Success

            assertEquals(
                expectedNotifications,
                success.data
            )

            coVerify(exactly = 1) {
                remoteDataSource.getNotifications()
            }
        }

    @Test
    fun `getNotifications deve retornar falha quando nao houver internet`() =
        runTest {
            coEvery {
                remoteDataSource.getNotifications()
            } throws IOException("Sem conexão")

            val result = repository.getNotifications()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Não foi possível conectar. Verifique sua internet.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getNotifications()
            }
        }

    @Test
    fun `getNotifications deve retornar falha quando ocorrer timeout`() =
        runTest {
            coEvery {
                remoteDataSource.getNotifications()
            } throws SocketTimeoutException("Timeout")

            val result = repository.getNotifications()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "A solicitação demorou mais que o esperado.",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getNotifications()
            }
        }

    @Test
    fun `getNotifications deve retornar mensagem da excecao desconhecida`() =
        runTest {
            coEvery {
                remoteDataSource.getNotifications()
            } throws IllegalStateException("Erro inesperado no datasource")

            val result = repository.getNotifications()

            assertTrue(result is AppResult.Failure)

            val failure = result as AppResult.Failure

            assertEquals(
                "Erro inesperado no datasource",
                failure.message
            )

            assertNull(failure.code)

            coVerify(exactly = 1) {
                remoteDataSource.getNotifications()
            }
        }

    @Test(expected = CancellationException::class)
    fun `getNotifications deve propagar cancelamento da coroutine`() =
        runTest {
            coEvery {
                remoteDataSource.getNotifications()
            } throws CancellationException("Operação cancelada")

            repository.getNotifications()
        }
}