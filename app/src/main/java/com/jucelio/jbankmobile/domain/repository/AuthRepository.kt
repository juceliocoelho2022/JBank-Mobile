package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.core.common.Result
import com.jucelio.jbankmobile.domain.model.User

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<User>

    suspend fun logout()

    suspend fun getLoggedUser(): User?

    suspend fun isLogged(): Boolean

}