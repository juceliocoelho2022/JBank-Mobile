package com.jucelio.jbankmobile.domain.usecase.transaction

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction
import com.jucelio.jbankmobile.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetStatementUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: GetStatementUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetStatementUseCase(repository)
    }

    @Test
    fun `deve retornar extrato`() = runTest {

        val statement = emptyList<Transaction>()

        coEvery {
            repository.getStatement(1L)
        } returns AppResult.Success(statement)

        val result = useCase(1L)

        assertTrue(result is AppResult.Success)

        assertEquals(
            statement,
            (result as AppResult.Success).data
        )

        coVerify(exactly = 1) {
            repository.getStatement(1L)
        }
    }

    @Test
    fun `deve retornar conta invalida`() = runTest {

        val result = useCase(0L)

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Conta inválida.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar falha do repositorio`() = runTest {

        coEvery {
            repository.getStatement(1L)
        } returns AppResult.Failure("Erro")

        val result = useCase(1L)

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Erro",
            (result as AppResult.Failure).message
        )
    }
}