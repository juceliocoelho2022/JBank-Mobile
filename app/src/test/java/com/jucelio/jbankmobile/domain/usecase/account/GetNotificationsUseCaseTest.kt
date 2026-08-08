package com.jucelio.jbankmobile.domain.usecase.account

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AccountRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAccountsUseCaseTest {

    private lateinit var repository: AccountRepository
    private lateinit var useCase: GetAccountsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetAccountsUseCase(repository)
    }

    @Test
    fun `deve retornar lista de contas`() = runTest {

        val accounts = emptyList<Account>()

        coEvery {
            repository.getAccounts()
        } returns AppResult.Success(accounts)

        val result = useCase()

        assertTrue(result is AppResult.Success)
        assertEquals(accounts, (result as AppResult.Success).data)

        coVerify(exactly = 1) {
            repository.getAccounts()
        }
    }

    @Test
    fun `deve retornar falha`() = runTest {

        coEvery {
            repository.getAccounts()
        } returns AppResult.Failure("Erro")

        val result = useCase()

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Erro",
            (result as AppResult.Failure).message
        )
    }
}