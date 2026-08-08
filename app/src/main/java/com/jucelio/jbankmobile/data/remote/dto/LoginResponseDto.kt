package com.jucelio.jbankmobile.data.remote.dto

data class LoginResponseDto(
    val token: String,
    val user: UserDto
)