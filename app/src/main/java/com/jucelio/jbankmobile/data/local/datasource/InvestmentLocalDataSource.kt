package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.InvestmentDao
import com.jucelio.jbankmobile.data.local.entity.InvestmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvestmentLocalDataSource @Inject constructor(
    private val dao: InvestmentDao
) {

    fun observeInvestments(): Flow<List<InvestmentEntity>> =
        dao.observeInvestments()

    suspend fun getInvestments(): List<InvestmentEntity> =
        dao.getInvestments()

    suspend fun getInvestmentById(id: Long): InvestmentEntity? =
        dao.getInvestmentById(id)

    suspend fun save(investment: InvestmentEntity) =
        dao.insertInvestment(investment)

    suspend fun saveAll(investments: List<InvestmentEntity>) =
        dao.replaceInvestments(investments)

    suspend fun clear() =
        dao.clearInvestments()
}
