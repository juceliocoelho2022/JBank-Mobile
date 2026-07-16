package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class TransactionRepository @Inject constructor(
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