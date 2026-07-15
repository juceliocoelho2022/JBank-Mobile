package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.local.TokenStore
import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.LoginRequest

class AuthRepository(
    private val api: JBankApi,
    private val tokenStore: TokenStore
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            val response = api.login(LoginRequest(email.trim(), password))
            require(response.token.isNotBlank()) {
                "A API retornou um token vazio."
            }
            tokenStore.saveToken(response.token)
        }
    }

    suspend fun logout() {
        tokenStore.clear()
    }
}
