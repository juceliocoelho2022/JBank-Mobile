package com.jucelio.jbankmobile.ui.account

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.usecase.account.GetAccountsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getAccountsUseCase: GetAccountsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getAccountsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load accounts successfully on init`() {

        val accounts = listOf(
            mockk<Account>(),
            mockk<Account>()
        )

        coEvery {
            getAccountsUseCase()
        } returns AppResult.Success(accounts)

        val viewModel = AccountViewModel(getAccountsUseCase)

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertEquals(accounts, viewModel.state.accounts)
        assertNull(viewModel.state.errorMessage)

        coVerify(exactly = 1) {
            getAccountsUseCase()
        }
    }

    @Test
    fun `should show error when use case fails`() {

        coEvery {
            getAccountsUseCase()
        } returns AppResult.Failure("Erro")

        val viewModel = AccountViewModel(getAccountsUseCase)

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertTrue(viewModel.state.accounts.isEmpty())
        assertEquals(
            "Erro",
            viewModel.state.errorMessage
        )
    }

    @Test
    fun `should reload accounts`() {

        coEvery {
            getAccountsUseCase()
        } returns AppResult.Success(emptyList())

        val viewModel = AccountViewModel(getAccountsUseCase)

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.loadAccounts()

        dispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) {
            getAccountsUseCase()
        }
    }

    @Test
    fun `should keep account list`() {

        val account = mockk<Account>()

        coEvery {
            getAccountsUseCase()
        } returns AppResult.Success(
            listOf(account)
        )

        val viewModel = AccountViewModel(getAccountsUseCase)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            1,
            viewModel.state.accounts.size
        )

        assertEquals(
            account,
            viewModel.state.accounts.first()
        )
    }

    @Test
    fun `should clear error after successful reload`() {

        coEvery {
            getAccountsUseCase()
        } returnsMany listOf(
            AppResult.Failure("Erro"),
            AppResult.Success(emptyList())
        )

        val viewModel = AccountViewModel(getAccountsUseCase)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            "Erro",
            viewModel.state.errorMessage
        )

        viewModel.loadAccounts()

        dispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.state.errorMessage)
    }
}