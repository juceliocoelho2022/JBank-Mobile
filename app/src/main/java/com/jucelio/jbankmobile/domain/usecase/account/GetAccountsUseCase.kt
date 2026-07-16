package com.jucelio.jbankmobile.domain.usecase.account

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(): AppResult<List<Account>> {
        return repository.getAccounts()
    }
}