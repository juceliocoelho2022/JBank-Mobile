package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.DashboardDao
import com.jucelio.jbankmobile.data.local.entity.DashboardEntity
import com.jucelio.jbankmobile.data.local.mapper.DashboardLocalMapper
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardLocalDataSource @Inject constructor(
    private val dao: DashboardDao
) {

    suspend fun saveDashboard(
        dto: DashboardResponseDto
    ) {
        dao.replaceDashboard(
            dashboard = DashboardLocalMapper.toDashboardEntity(dto),
            transactions = DashboardLocalMapper.toTransactionEntities(dto)
        )
    }

    suspend fun getDashboard(): Dashboard? {

        val dashboard =
            dao.getDashboard() ?: return null

        val transactions =
            dao.getLatestTransactions()

        return DashboardLocalMapper.toDomain(
            dashboard = dashboard,
            transactions = transactions
        )
    }

    suspend fun clear() {
        dao.clearAll()
    }
}