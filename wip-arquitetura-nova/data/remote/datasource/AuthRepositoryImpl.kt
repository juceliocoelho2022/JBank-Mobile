package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.AuthRemoteDataSource
import com.jucelio.jbankmobile.domain.model.User
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(

    private val remote: AuthRemoteDataSource

) : AuthRepository {

    private var loggedUser: User? = null

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        return runCatching {

            val response = remoteDataSource.login(
                email,
                password
            )

            val user = response.user.toDomain()

            loggedUser = user

            user

        }
    }

    override suspend fun logout() {
        loggedUser = null
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<User> {
        TODO("Implementar")
    }

    override suspend fun getLoggedUser(): User? {
        return loggedUser
    }

    override suspend fun isLogged(): Boolean {
        return loggedUser != null
    }
}