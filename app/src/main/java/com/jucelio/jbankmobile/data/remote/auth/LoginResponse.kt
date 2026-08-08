package com.jucelio.jbankmobile.data.remote.auth

data class LoginResponse(
    val token: String,
    val type: String? = "Bearer",
    val expiresIn: Long? = null
)