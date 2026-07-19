package com.jucelio.jbankmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jucelio.jbankmobile.data.local.entity.DashboardEntity
import com.jucelio.jbankmobile.data.local.entity.DashboardTransactionEntity

@Dao
interface DashboardDao {

    @Query(
        """
        SELECT *
        FROM dashboard
        WHERE id = 1
        LIMIT 1
        """
    )
    suspend fun getDashboard(): DashboardEntity?

    @Query(
        """
        SELECT *
        FROM dashboard_transactions
        ORDER BY position ASC
        """
    )
    suspend fun getLatestTransactions():
            List<DashboardTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDashboard(
        dashboard: DashboardEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(
        transactions: List<DashboardTransactionEntity>
    )

    @Query("DELETE FROM dashboard_transactions")
    suspend fun deleteTransactions()

    @Query("DELETE FROM dashboard")
    suspend fun deleteDashboard()

    @Transaction
    suspend fun replaceDashboard(
        dashboard: DashboardEntity,
        transactions: List<DashboardTransactionEntity>
    ) {
        deleteTransactions()
        insertDashboard(dashboard)

        if (transactions.isNotEmpty()) {
            insertTransactions(transactions)
        }
    }

    @Transaction
    suspend fun clearAll() {
        deleteTransactions()
        deleteDashboard()
    }
}