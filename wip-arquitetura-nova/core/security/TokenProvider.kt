package com.jucelio.jbankmobile.core.security

interface TokenProvider {

    suspend fun saveToken(
        token: String
    )

    suspend fun getToken(): String?

    suspend fun clearToken()

}