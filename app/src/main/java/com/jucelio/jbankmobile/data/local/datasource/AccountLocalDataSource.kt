package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.AccountDao
import com.jucelio.jbankmobile.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountLocalDataSource @Inject constructor(
    private val dao: AccountDao
) {

    fun observeAccounts(): Flow<List<AccountEntity>> =
        dao.observeAccounts()

    suspend fun getAccounts(): List<AccountEntity> =
        dao.getAccounts()


    suspend fun getAccountById(id: Long): AccountEntity? =
        dao.getAccountById(id)

    suspend fun save(account: AccountEntity) =
        dao.insertAccount(account)

    suspend fun saveAll(accounts: List<AccountEntity>) =
        dao.replaceAccounts(accounts)

    suspend fun clear() =
        dao.clearAccounts()
}