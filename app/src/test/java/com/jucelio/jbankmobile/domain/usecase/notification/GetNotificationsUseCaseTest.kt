package com.jucelio.jbankmobile.domain.usecase.notification

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.repository.NotificationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetNotificationsUseCaseTest {

    private lateinit var repository: NotificationRepository
    private lateinit var useCase: GetNotificationsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetNotificationsUseCase(repository)
    }

    @Test
    fun `deve retornar notificacoes`() = runTest {

        val notifications = emptyList<Notification>()

        coEvery {
            repository.getNotifications()
        } returns AppResult.Success(notifications)

        val result = useCase()

        assertTrue(result is AppResult.Success)
        assertEquals(notifications, (result as AppResult.Success).data)

        coVerify(exactly = 1) {
            repository.getNotifications()
        }
    }

    @Test
    fun `deve retornar falha`() = runTest {

        coEvery {
            repository.getNotifications()
        } returns AppResult.Failure("Erro")

        val result = useCase()

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Erro",
            (result as AppResult.Failure).message
        )
    }
}