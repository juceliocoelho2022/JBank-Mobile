package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto
import com.jucelio.jbankmobile.domain.model.Account

fun AccountResponseDto.toDomain(): Account {

    return Account(
        id = id,
        number = number,
        type = type,
        balance = balance,
        active = active,
        customerId = customerId
    )
}