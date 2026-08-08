package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.session.SessionManager
import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.LoginRequest
import com.jucelio.jbankmobile.data.remote.dto.LoginResponse
import com.jucelio.jbankmobile.domain.model.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class AuthRepositoryImplTest {

    private lateinit var api: JBankApi
    private lateinit var sessionManager: SessionManager
    private lateinit var repository: AuthRepositoryImpl

    private val email = "admin@jbank.com"
    private val password = "123456"
    private val accessToken = "jwt-access-token"

    @Before
    fun setup() {
        api = mockk()
        sessionManager = mockk()

        repository = AuthRepositoryImpl(
            api = api,
            sessionManager = sessionManager
        )
    }

    @Test
    fun `login deve salvar token e retornar sucesso`() = runTest {
        val request = LoginRequest(email, password)
        val response = LoginResponse(token = accessToken)

        coEvery { api.login(request) } returns response
        coEvery { sessionManager.saveAccessToken(accessToken) } returns Unit

        val result = repository.login(email, password)

        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)

        coVerify(exactly = 1) { api.login(request) }
        coVerify(exactly = 1) {
            sessionManager.saveAccessToken(accessToken)
        }

        confirmVerified(api, sessionManager)
    }

    @Test
    fun `login deve retornar failure quando token estiver vazio`() = runTest {
        val request = LoginRequest(email, password)

        coEvery { api.login(request) } returns LoginResponse(token = "")

        val result = repository.login(email, password)

        assertTrue(result is AppResult.Failure)
        assertEquals(
            "A API retornou um token vazio.",
            (result as AppResult.Failure).message
        )

        coVerify(exactly = 1) { api.login(request) }
        coVerify(exactly = 0) {
            sessionManager.saveAccessToken(any())
        }

        confirmVerified(api, sessionManager)
    }

    @Test
    fun `login deve retornar failure quando token contiver apenas espacos`() =
        runTest {
            val request = LoginRequest(email, password)

            coEvery {
                api.login(request)
            } returns LoginResponse(token = "   ")

            val result = repository.login(email, password)

            assertTrue(result is AppResult.Failure)
            assertEquals(
                "A API retornou um token vazio.",
                (result as AppResult.Failure).message
            )

            coVerify(exactly = 1) { api.login(request) }
            coVerify(exactly = 0) {
                sessionManager.saveAccessToken(any())
            }

            confirmVerified(api, sessionManager)
        }

    @Test
    fun `login deve retornar failure quando nao houver conexao`() = runTest {
        val request = LoginRequest(email, password)

        coEvery { api.login(request) } throws IOException()

        val result = repository.login(email, password)

        assertTrue(result is AppResult.Failure)
        assertEquals(
            "Não foi possível conectar. Verifique sua internet.",
            (result as AppResult.Failure).message
        )

        coVerify(exactly = 1) { api.login(request) }
        coVerify(exactly = 0) {
            sessionManager.saveAccessToken(any())
        }

        confirmVerified(api, sessionManager)
    }

    @Test
    fun `login deve retornar failure quando ocorrer timeout`() = runTest {
        val request = LoginRequest(email, password)

        coEvery {
            api.login(request)
        } throws SocketTimeoutException()

        val result = repository.login(email, password)

        assertTrue(result is AppResult.Failure)
        assertEquals(
            "A solicitação demorou mais que o esperado.",
            (result as AppResult.Failure).message
        )

        coVerify(exactly = 1) { api.login(request) }
        coVerify(exactly = 0) {
            sessionManager.saveAccessToken(any())
        }

        confirmVerified(api, sessionManager)
    }

    @Test
    fun `login deve retornar failure quando ocorrer erro inesperado`() =
        runTest {
            val request = LoginRequest(email, password)

            coEvery {
                api.login(request)
            } throws IllegalStateException("Erro inesperado")

            val result = repository.login(email, password)

            assertTrue(result is AppResult.Failure)
            assertEquals(
                "Erro inesperado",
                (result as AppResult.Failure).message
            )

            coVerify(exactly = 1) { api.login(request) }
            coVerify(exactly = 0) {
                sessionManager.saveAccessToken(any())
            }

            confirmVerified(api, sessionManager)
        }

    @Test(expected = CancellationException::class)
    fun `login deve propagar cancellation exception`() = runTest {
        val request = LoginRequest(email, password)

        coEvery {
            api.login(request)
        } throws CancellationException("Coroutine cancelada")

        repository.login(email, password)
    }

    @Test
    fun `logout deve remover sessao e retornar sucesso`() = runTest {
        coEvery { sessionManager.logout() } returns Unit

        val result = repository.logout()

        assertTrue(result is AppResult.Success)
        assertEquals(Unit, (result as AppResult.Success).data)

        coVerify(exactly = 1) { sessionManager.logout() }
        confirmVerified(sessionManager)
    }

    @Test
    fun `logout deve retornar failure com mensagem da excecao`() = runTest {
        coEvery {
            sessionManager.logout()
        } throws IllegalStateException("Erro ao remover sessão")

        val result = repository.logout()

        assertTrue(result is AppResult.Failure)
        assertEquals(
            "Erro ao remover sessão",
            (result as AppResult.Failure).message
        )

        coVerify(exactly = 1) { sessionManager.logout() }
        confirmVerified(sessionManager)
    }

    @Test
    fun `logout deve retornar mensagem padrao quando excecao nao tiver mensagem`() =
        runTest {
            coEvery {
                sessionManager.logout()
            } throws RuntimeException()

            val result = repository.logout()

            assertTrue(result is AppResult.Failure)
            assertEquals(
                "Não foi possível encerrar a sessão.",
                (result as AppResult.Failure).message
            )

            coVerify(exactly = 1) { sessionManager.logout() }
            confirmVerified(sessionManager)
        }

    @Test(expected = CancellationException::class)
    fun `logout deve propagar cancellation exception`() = runTest {
        coEvery {
            sessionManager.logout()
        } throws CancellationException("Logout cancelado")

        repository.logout()
    }
}
