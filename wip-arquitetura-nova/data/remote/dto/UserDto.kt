package com.jucelio.jbankmobile.data.remote.dto

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val cpf: String,
    val phone: String,
    val createdAt: String,
    val active: Boolean
)