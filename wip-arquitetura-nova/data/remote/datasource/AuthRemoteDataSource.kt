package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.api.AuthApi
import com.jucelio.jbankmobile.data.remote.dto.LoginRequestDto
import jakarta.inject.Inject

class AuthRemoteDataSource @Inject constructor(

    private val api: AuthApi

) {

    suspend fun login(
        email: String,
        password: String
    ) =
        api.login(
            LoginRequestDto(
                email,
                password
            )
        )

}