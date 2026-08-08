package com.jucelio.jbankmobile.domain.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val cpf: String,
    val phone: String,
    val createdAt: String,
    val active: Boolean
)