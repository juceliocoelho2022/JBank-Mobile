package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.Transaction
import java.math.BigDecimal

interface AccountRepository {

    suspend fun getAccount(): Account

    suspend fun deposit(
        amount: BigDecimal
    )

    suspend fun withdraw(
        amount: BigDecimal
    )

    suspend fun getStatement(): List<Transaction>
}