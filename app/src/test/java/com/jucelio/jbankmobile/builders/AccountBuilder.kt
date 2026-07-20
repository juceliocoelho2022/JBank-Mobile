package com.jucelio.jbankmobile.builders

import com.jucelio.jbankmobile.domain.model.Account
import java.math.BigDecimal

class AccountBuilder {

    private var id: Long = 1L
    private var number: String = "00012345-6"
    private var type: String = "Conta Corrente"
    private var balance: BigDecimal = BigDecimal("1500.75")
    private var active: Boolean = true
    private var customerId: Long? = 100L

    fun withId(value: Long) = apply {
        id = value
    }

    fun withNumber(value: String) = apply {
        number = value
    }

    fun withType(value: String) = apply {
        type = value
    }

    fun withBalance(value: BigDecimal) = apply {
        balance = value
    }

    fun withActive(value: Boolean) = apply {
        active = value
    }

    fun withCustomerId(value: Long?) = apply {
        customerId = value
    }

    fun asInactive() = apply {
        active = false
    }

    fun build(): Account {
        return Account(
            id = id,
            number = number,
            type = type,
            balance = balance,
            active = active,
            customerId = customerId
        )
    }
}