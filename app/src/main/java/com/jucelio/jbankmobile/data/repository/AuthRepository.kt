package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.core.session.SessionManager
import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: JBankApi,
    private val sessionManager: SessionManager
) {

    suspend fun login(
        email: String,
        password: String
    ): ApiResult<Unit> {
        return when (
            val result = safeApiCall {
                api.login(
                    LoginRequest(
                        email = email.trim(),
                        password = password
                    )
                )
            }
        ) {
            is ApiResult.Success -> {
                val token = result.data.token

                if (token.isBlank()) {
                    ApiResult.Error(
                        type = com.jucelio.jbankmobile.core.network.NetworkError.UNKNOWN,
                        message = "A API retornou um token vazio."
                    )
                } else {
                    sessionManager.saveAccessToken(token)

                    ApiResult.Success(Unit)
                }
            }

            is ApiResult.Error -> result
        }
    }

    suspend fun logout() {
        sessionManager.logout()
    }
}