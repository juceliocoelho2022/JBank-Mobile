package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

data class Account(
    val id: Long,
    val agency: String,
    val number: String,
    val balance: BigDecimal,
    val type: AccountType
)

enum class AccountType {
    CHECKING,
    SAVINGS
}