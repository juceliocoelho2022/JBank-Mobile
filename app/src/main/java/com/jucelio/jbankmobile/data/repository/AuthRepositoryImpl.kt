package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.core.session.SessionManager
import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.LoginRequest
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: JBankApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): AppResult<Unit> {
        return when (
            val result = safeApiCall {
                api.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )
            }
        ) {
            is ApiResult.Success -> {
                val token = result.data.token

                if (token.isBlank()) {
                    AppResult.Failure(
                        message = "A API retornou um token vazio."
                    )
                } else {
                    sessionManager.saveAccessToken(token)

                    AppResult.Success(Unit)
                }
            }

            is ApiResult.Error -> {
                AppResult.Failure(
                    message = result.message,
                    code = result.code
                )
            }
        }
    }

    override suspend fun logout(): AppResult<Unit> {
        return try {
            sessionManager.logout()
            AppResult.Success(Unit)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            AppResult.Failure(
                message = error.message
                    ?: "Não foi possível encerrar a sessão."
            )
        }
    }
}