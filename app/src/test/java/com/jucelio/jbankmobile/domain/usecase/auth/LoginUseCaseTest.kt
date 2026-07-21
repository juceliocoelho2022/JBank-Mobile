package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = LoginUseCase(repository)
    }

    @Test
    fun `deve retornar sucesso quando login for valido`() = runTest {

        coEvery {
            repository.login(any(), any())
        } returns AppResult.Success(Unit)

        val result = useCase(
            "jucelio@email.com",
            "123456"
        )

        assertTrue(result is AppResult.Success)

        coVerify(exactly = 1) {
            repository.login(
                "jucelio@email.com",
                "123456"
            )
        }
    }

    @Test
    fun `deve retornar erro quando email estiver vazio`() = runTest {

        val result = useCase(
            "",
            "123456"
        )

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Informe o seu e-mail.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar erro quando email for invalido`() = runTest {

        val result = useCase(
            "abc",
            "123456"
        )

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Informe um e-mail válido.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar erro quando senha estiver vazia`() = runTest {

        val result = useCase(
            "teste@email.com",
            ""
        )

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "Informe a sua senha.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve retornar erro quando senha for menor que seis caracteres`() = runTest {

        val result = useCase(
            "teste@email.com",
            "123"
        )

        assertTrue(result is AppResult.Failure)

        assertEquals(
            "A senha deve possuir pelo menos 6 caracteres.",
            (result as AppResult.Failure).message
        )
    }

    @Test
    fun `deve normalizar email antes de enviar ao repositorio`() = runTest {

        coEvery {
            repository.login(any(), any())
        } returns AppResult.Success(Unit)

        useCase(
            "  JUCELIO@EMAIL.COM ",
            "123456"
        )

        coVerify {
            repository.login(
                "jucelio@email.com",
                "123456"
            )
        }
    }
}