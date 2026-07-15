package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto

class TransactionRepository(
    private val api: JBankApi
) {

    suspend fun getStatement(
        accountId: Long
    ): Result<List<TransactionResponseDto>> {
        return runCatching {
            api.getStatement(accountId)
        }
    }
}