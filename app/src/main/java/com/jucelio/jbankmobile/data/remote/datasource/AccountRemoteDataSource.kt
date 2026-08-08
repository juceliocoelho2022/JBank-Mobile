package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.AccountResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRemoteDataSource @Inject constructor(
    private val api: JBankApi
) {

    suspend fun getAccounts(): List<AccountResponseDto> {
        return api.getAccounts()
    }
}