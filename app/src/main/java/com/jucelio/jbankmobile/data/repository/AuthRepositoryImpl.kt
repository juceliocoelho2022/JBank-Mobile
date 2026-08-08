package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.local.TokenDataStore
import com.jucelio.jbankmobile.data.remote.auth.AuthApi
import com.jucelio.jbankmobile.data.remote.auth.LoginRequest
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): AppResult<Unit> {
        return try {
            val response = authApi.login(
                request = LoginRequest(
                    email = email,
                    password = password
                )
            )

            if (response.isSuccessful) {
                AppResult.Success(Unit)
            } else {
                AppResult.Failure(
                    message = mapErrorMessage(
                        code = response.code()
                    )
                )
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            AppResult.Failure(
                message = "Não foi possível conectar ao servidor."
            )
        }
    }

    private fun mapErrorMessage(
        code: Int
    ): String {
        return when (code) {
            400 -> "Dados de acesso inválidos."
            401 -> "E-mail ou senha incorretos."
            403 -> "Acesso não autorizado."
            404 -> "Usuário não encontrado."
            500 -> "Erro interno no servidor."
            else -> "Não foi possível acessar sua conta."
        }
    }
    override suspend fun logout(): AppResult<Unit> {
        return try {
            tokenDataStore.clearSession()

            AppResult.Success(Unit)
        } catch (exception: Exception) {
            AppResult.Failure(
                message = exception.message
                    ?: "Não foi possível encerrar a sessão."
            )
        }
    }
}