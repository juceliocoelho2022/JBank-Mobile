package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult

/**
 * Contrato de autenticação utilizado pela camada de domínio.
 *
 * A camada domain não conhece Retrofit, DataStore ou Hilt.
 */
interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): AppResult<Unit>

    suspend fun logout(): AppResult<Unit>
}