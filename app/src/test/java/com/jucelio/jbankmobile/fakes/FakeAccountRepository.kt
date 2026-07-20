package com.jucelio.jbankmobile.fakes

import com.jucelio.jbankmobile.domain.model.Account
import com.jucelio.jbankmobile.fixtures.account.AccountFixtures

class FakeAccountRepository {

    var accounts: List<Account> = AccountFixtures.accountList()

    var shouldFail: Boolean = false

    var errorMessage: String = "Erro ao carregar contas"

    var getAccountsCalls: Int = 0
        private set

    fun returnAccounts(value: List<Account>) {
        accounts = value
        shouldFail = false
    }

    fun returnFailure(
        message: String = "Erro ao carregar contas"
    ) {
        shouldFail = true
        errorMessage = message
    }

    fun registerGetAccountsCall() {
        getAccountsCalls++
    }

    fun reset() {
        accounts = AccountFixtures.accountList()
        shouldFail = false
        errorMessage = "Erro ao carregar contas"
        getAccountsCalls = 0
    }
}