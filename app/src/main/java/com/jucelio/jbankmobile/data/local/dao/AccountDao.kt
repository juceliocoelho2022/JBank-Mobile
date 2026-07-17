package com.jucelio.jbankmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jucelio.jbankmobile.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query(
        """
        SELECT *
        FROM accounts
        ORDER BY id ASC
        """
    )
    fun observeAccounts(): Flow<List<AccountEntity>>

    @Query(
        """
        SELECT *
        FROM accounts
        ORDER BY id ASC
        """
    )
    suspend fun getAccounts(): List<AccountEntity>

    @Query(
        """
        SELECT *
        FROM accounts
        WHERE id = :accountId
        LIMIT 1
        """
    )
    suspend fun getAccountById(
        accountId: Long
    ): AccountEntity?

    @Query(
        """
        SELECT *
        FROM accounts
        WHERE active = 1
        ORDER BY id ASC
        """
    )
    suspend fun getActiveAccounts(): List<AccountEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAccount(
        account: AccountEntity
    )

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAccounts(
        accounts: List<AccountEntity>
    )

    @Query(
        """
        DELETE FROM accounts
        WHERE id = :accountId
        """
    )
    suspend fun deleteAccountById(
        accountId: Long
    )

    @Query(
        """
        DELETE FROM accounts
        """
    )
    suspend fun clearAccounts()

    @Transaction
    suspend fun replaceAccounts(
        accounts: List<AccountEntity>
    ) {
        clearAccounts()
        insertAccounts(accounts)
    }
}