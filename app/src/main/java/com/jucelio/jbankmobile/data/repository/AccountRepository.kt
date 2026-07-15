package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto

class AccountRepository(
    private val api: JBankApi
) {

    suspend fun getAccounts(): Result<List<AccountResponseDto>> {
        return runCatching {
            api.getAccounts()
        }
    }
}