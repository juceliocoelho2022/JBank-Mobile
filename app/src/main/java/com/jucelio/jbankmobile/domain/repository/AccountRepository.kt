package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult

interface  AccountRepository {
    suspend fun getAccounts(): AppResult<List<Account>>
}