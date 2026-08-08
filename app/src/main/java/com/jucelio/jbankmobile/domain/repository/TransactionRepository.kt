package com.jucelio.jbankmobile.domain.repository

import java.math.BigDecimal

interface TransferRepository {

    suspend fun transfer(
        agency: String,
        account: String,
        amount: BigDecimal
    )
}