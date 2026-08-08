package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.common.Result
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.AuthRemoteDataSource
import com.jucelio.jbankmobile.domain.model.User
import com.jucelio.jbankmobile.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
) : AuthRepository {

    private var loggedUser: User? = null

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        return try {

            val response = remote.login(
                email,
                password
            )

            val user = response.user.toDomain()

            loggedUser = user

            Result.Success(user)

        } catch (e: Exception) {

            Result.Error(e)

        }
    }

    override suspend fun logout() {

        loggedUser = null

    }

    override suspend fun getLoggedUser(): User? {

        return loggedUser

    }

    override suspend fun isLogged(): Boolean {

        return loggedUser != null

    }

}