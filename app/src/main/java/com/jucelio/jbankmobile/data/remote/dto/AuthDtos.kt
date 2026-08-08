package com.jucelio.jbankmobile.data.remote.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)
