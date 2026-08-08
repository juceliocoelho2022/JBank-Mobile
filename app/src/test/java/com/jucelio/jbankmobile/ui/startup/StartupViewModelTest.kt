package com.jucelio.jbankmobile.ui.startup

import com.jucelio.jbankmobile.core.session.SessionManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StartupViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var sessionManager: SessionManager

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        sessionManager = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should navigate to HOME when user is authenticated`() {
        coEvery {
            sessionManager.isAuthenticated()
        } returns true

        val viewModel = StartupViewModel(sessionManager)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            StartupDestination.HOME,
            viewModel.destination
        )
    }

    @Test
    fun `should navigate to LOGIN when user is not authenticated`() {
        coEvery {
            sessionManager.isAuthenticated()
        } returns false

        val viewModel = StartupViewModel(sessionManager)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            StartupDestination.LOGIN,
            viewModel.destination
        )
    }

    @Test
    fun `should navigate to LOGIN when exception occurs`() {
        coEvery {
            sessionManager.isAuthenticated()
        } throws RuntimeException()

        val viewModel = StartupViewModel(sessionManager)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            StartupDestination.LOGIN,
            viewModel.destination
        )
    }

    @Test
    fun `destination should initially be null before coroutine finishes`() {
        coEvery {
            sessionManager.isAuthenticated()
        } returns true

        val viewModel = StartupViewModel(sessionManager)

        assertEquals(
            null,
            viewModel.destination
        )
    }

    @Test
    fun `destination should change after coroutine execution`() {
        coEvery {
            sessionManager.isAuthenticated()
        } returns true

        val viewModel = StartupViewModel(sessionManager)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            StartupDestination.HOME,
            viewModel.destination
        )
    }
}