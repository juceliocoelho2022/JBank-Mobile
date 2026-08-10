package com.jucelio.jbankmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jucelio.jbankmobile.data.local.entity.InvestmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestmentDao {

    @Query(
        """
        SELECT *
        FROM investments
        ORDER BY id ASC
        """
    )
    fun observeInvestments(): Flow<List<InvestmentEntity>>

    @Query(
        """
        SELECT *
        FROM investments
        ORDER BY id ASC
        """
    )
    suspend fun getInvestments(): List<InvestmentEntity>

    @Query(
        """
        SELECT *
        FROM investments
        WHERE id = :investmentId
        LIMIT 1
        """
    )
    suspend fun getInvestmentById(
        investmentId: Long
    ): InvestmentEntity?

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertInvestment(
        investment: InvestmentEntity
    )

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertInvestments(
        investments: List<InvestmentEntity>
    )

    @Query(
        """
        DELETE FROM investments
        WHERE id = :investmentId
        """
    )
    suspend fun deleteInvestmentById(
        investmentId: Long
    )

    @Query(
        """
        DELETE FROM investments
        """
    )
    suspend fun clearInvestments()

    @Transaction
    suspend fun replaceInvestments(
        investments: List<InvestmentEntity>
    ) {
        clearInvestments()
        insertInvestments(investments)
    }
}
