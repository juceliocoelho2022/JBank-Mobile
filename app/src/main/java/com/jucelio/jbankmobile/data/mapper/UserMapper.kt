package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.UserDto
import com.jucelio.jbankmobile.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    cpf = cpf,
    phone = phone,
    createdAt = createdAt,
    active = active
)