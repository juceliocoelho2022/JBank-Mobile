package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Transaction

interface TransactionRepository {

    suspend fun getStatement(
        accountId: Long
    ): AppResult<List<Transaction>>
}