package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.TransactionResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRemoteDataSource @Inject constructor(
    private val api: JBankApi
) {

    suspend fun getStatement(
        accountId: Long
    ): List<TransactionResponseDto> {
        return api.getStatement(accountId)
    }
}