package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: LogoutUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = LogoutUseCase(repository)
    }

    @Test
    fun `deve retornar sucesso`() = runTest {

        coEvery {
            repository.logout()
        } returns AppResult.Success(Unit)

        val result = useCase()

        assertTrue(result is AppResult.Success)
    }

    @Test
    fun `deve retornar erro`() = runTest {

        coEvery {
            repository.logout()
        } returns AppResult.Failure("Erro")

        val result = useCase()

        assertTrue(result is AppResult.Failure)
    }
}