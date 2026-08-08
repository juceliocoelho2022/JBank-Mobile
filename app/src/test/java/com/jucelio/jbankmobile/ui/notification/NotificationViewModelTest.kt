package com.jucelio.jbankmobile.ui.notification

import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.usecase.notification.GetNotificationsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getNotificationsUseCase: GetNotificationsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getNotificationsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load notifications successfully`() {

        val notifications = listOf(
            mockk<Notification>(),
            mockk<Notification>()
        )

        coEvery {
            getNotificationsUseCase()
        } returns AppResult.Success(notifications)

        val viewModel = NotificationViewModel(
            getNotificationsUseCase
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertEquals(
            notifications,
            viewModel.state.notifications
        )
        assertNull(viewModel.state.errorMessage)

        coVerify(exactly = 1) {
            getNotificationsUseCase()
        }
    }

    @Test
    fun `should return error when use case fails`() {

        coEvery {
            getNotificationsUseCase()
        } returns AppResult.Failure(
            message = "Erro ao carregar notificações"
        )

        val viewModel = NotificationViewModel(
            getNotificationsUseCase
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertTrue(viewModel.state.notifications.isEmpty())
        assertEquals(
            "Erro ao carregar notificações",
            viewModel.state.errorMessage
        )
    }

    @Test
    fun `should reload notifications`() {

        coEvery {
            getNotificationsUseCase()
        } returns AppResult.Success(emptyList())

        val viewModel = NotificationViewModel(
            getNotificationsUseCase
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.loadNotifications()

        dispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) {
            getNotificationsUseCase()
        }
    }

    @Test
    fun `should keep notification list`() {

        val notification = mockk<Notification>()

        coEvery {
            getNotificationsUseCase()
        } returns AppResult.Success(
            listOf(notification)
        )

        val viewModel = NotificationViewModel(
            getNotificationsUseCase
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            1,
            viewModel.state.notifications.size
        )

        assertEquals(
            notification,
            viewModel.state.notifications.first()
        )
    }

    @Test
    fun `should clear previous error after success`() {

        coEvery {
            getNotificationsUseCase()
        } returnsMany listOf(
            AppResult.Failure("Erro"),
            AppResult.Success(emptyList())
        )

        val viewModel = NotificationViewModel(
            getNotificationsUseCase
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            "Erro",
            viewModel.state.errorMessage
        )

        viewModel.loadNotifications()

        dispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.state.errorMessage)
    }
}