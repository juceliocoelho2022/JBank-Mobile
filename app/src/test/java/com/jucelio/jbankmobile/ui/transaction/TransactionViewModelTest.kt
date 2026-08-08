package com.jucelio.jbankmobile.ui.transaction

import androidx.lifecycle.SavedStateHandle
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.usecase.transaction.GetStatementUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
class TransactionViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getStatementUseCase: GetStatementUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {

        Dispatchers.setMain(dispatcher)

        getStatementUseCase = mockk()

        savedStateHandle = mockk()

        every {
            savedStateHandle.get<Long>("accountId")
        } returns 1L
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load transactions successfully`() {

        val transactions = listOf(
            mockk<Transaction>(),
            mockk<Transaction>()
        )

        coEvery {
            getStatementUseCase(1L)
        } returns AppResult.Success(transactions)

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertEquals(
            transactions,
            viewModel.state.transactions
        )
        assertNull(viewModel.state.errorMessage)

        coVerify(exactly = 1) {
            getStatementUseCase(1L)
        }
    }

    @Test
    fun `should show error when loading fails`() {

        coEvery {
            getStatementUseCase(1L)
        } returns AppResult.Failure("Erro")

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.isLoading)
        assertTrue(viewModel.state.transactions.isEmpty())
        assertEquals(
            "Erro",
            viewModel.state.errorMessage
        )
    }

    @Test
    fun `should reload transactions`() {

        coEvery {
            getStatementUseCase(1L)
        } returns AppResult.Success(emptyList())

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.loadTransactions()

        dispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) {
            getStatementUseCase(1L)
        }
    }

    @Test
    fun `should change filter to income`() {

        coEvery {
            getStatementUseCase(any())
        } returns AppResult.Success(emptyList())

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.changeFilter(
            TransactionFilter.INCOME
        )

        assertEquals(
            TransactionFilter.INCOME,
            viewModel.state.filter
        )
    }

    @Test
    fun `should change filter to expense`() {

        coEvery {
            getStatementUseCase(any())
        } returns AppResult.Success(emptyList())

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.changeFilter(
            TransactionFilter.EXPENSE
        )

        assertEquals(
            TransactionFilter.EXPENSE,
            viewModel.state.filter
        )
    }

    @Test
    fun `should return all transactions when filter is all`() {

        val deposit = mockk<Transaction> {
            every { type } returns "DEPOSIT"
        }

        val pix = mockk<Transaction> {
            every { type } returns "PIX"
        }

        coEvery {
            getStatementUseCase(any())
        } returns AppResult.Success(
            listOf(
                deposit,
                pix
            )
        )

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            2,
            viewModel.state.filteredTransactions.size
        )
    }

    @Test
    fun `should filter only income transactions`() {

        val deposit = mockk<Transaction> {
            every { type } returns "DEPOSIT"
        }

        val pix = mockk<Transaction> {
            every { type } returns "PIX"
        }

        coEvery {
            getStatementUseCase(any())
        } returns AppResult.Success(
            listOf(
                deposit,
                pix
            )
        )

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.changeFilter(
            TransactionFilter.INCOME
        )

        assertEquals(
            1,
            viewModel.state.filteredTransactions.size
        )
    }

    @Test
    fun `should filter only expense transactions`() {

        val deposit = mockk<Transaction> {
            every { type } returns "DEPOSIT"
        }

        val pix = mockk<Transaction> {
            every { type } returns "PIX"
        }

        coEvery {
            getStatementUseCase(any())
        } returns AppResult.Success(
            listOf(
                deposit,
                pix
            )
        )

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        viewModel.changeFilter(
            TransactionFilter.EXPENSE
        )

        assertEquals(
            1,
            viewModel.state.filteredTransactions.size
        )
    }

    @Test
    fun `should clear error after successful reload`() {

        coEvery {
            getStatementUseCase(any())
        } returnsMany listOf(
            AppResult.Failure("Erro"),
            AppResult.Success(emptyList())
        )

        val viewModel = TransactionViewModel(
            getStatementUseCase,
            savedStateHandle
        )

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            "Erro",
            viewModel.state.errorMessage
        )

        viewModel.loadTransactions()

        dispatcher.scheduler.advanceUntilIdle()

        assertNull(
            viewModel.state.errorMessage
        )
    }
}