package com.jucelio.jbankmobile.fixtures.account

import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto
import com.jucelio.jbankmobile.domain.model.Account
import java.math.BigDecimal

object AccountFixtures {

    fun account() = Account(
        id = 1L,
        number = "00012345-6",
        type = "Conta Corrente",
        balance = BigDecimal("1500.75"),
        active = true,
        customerId = 100L
    )

    fun accountResponseDto() = AccountResponseDto(
        id = 1L,
        number = "00012345-6",
        type = "Conta Corrente",
        balance = BigDecimal("1500.75"),
        active = true,
        customerId = 100L
    )

    fun savingsAccount() = Account(
        id = 2L,
        number = "00098765-4",
        type = "Conta Poupança",
        balance = BigDecimal("3200.00"),
        active = true,
        customerId = 101L
    )

    fun inactiveAccount() = Account(
        id = 3L,
        number = "00011111-0",
        type = "Conta Corrente",
        balance = BigDecimal.ZERO,
        active = false,
        customerId = 102L
    )

    fun accountWithoutCustomer() = Account(
        id = 4L,
        number = "00022222-0",
        type = "Conta Corrente",
        balance = BigDecimal("500.00"),
        active = true,
        customerId = null
    )

    fun zeroBalanceAccount() = Account(
        id = 5L,
        number = "00033333-0",
        type = "Conta Corrente",
        balance = BigDecimal.ZERO,
        active = true,
        customerId = 103L
    )

    fun accountList() = listOf(
        account(),
        savingsAccount(),
        inactiveAccount()
    )

    fun accountResponseDtoList() = listOf(
        accountResponseDto(),
        AccountResponseDto(
            id = 2L,
            number = "00098765-4",
            type = "Conta Poupança",
            balance = BigDecimal("3200.00"),
            active = true,
            customerId = 101L
        ),
        AccountResponseDto(
            id = 3L,
            number = "00011111-0",
            type = "Conta Corrente",
            balance = BigDecimal.ZERO,
            active = false,
            customerId = 102L
        )
    )

    fun emptyAccountList(): List<Account> = emptyList()

    fun emptyAccountResponseDtoList(): List<AccountResponseDto> = emptyList()

    fun accountWithBalance(
        balance: BigDecimal
    ) = account().copy(
        balance = balance
    )

    fun accountWithId(
        id: Long
    ) = account().copy(
        id = id
    )

    fun accountWithCustomerId(
        customerId: Long?
    ) = account().copy(
        customerId = customerId
    )
}