package com.jucelio.jbankmobile.data.remote.api

import com.jucelio.jbankmobile.data.remote.dto.LoginRequestDto
import com.jucelio.jbankmobile.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto
}