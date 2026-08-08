package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

/**
 * Modelo de domínio da conta bancária.
 */
data class Account(
    val id: Long,
    val number: String,
    val type: String,
    val balance: BigDecimal,
    val active: Boolean,
    val customerId: Long?
)