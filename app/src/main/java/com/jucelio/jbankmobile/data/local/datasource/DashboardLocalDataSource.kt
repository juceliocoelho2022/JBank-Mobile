package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.DashboardDao
import com.jucelio.jbankmobile.data.local.mapper.DashboardLocalMapper
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import com.jucelio.jbankmobile.domain.model.Dashboard
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardLocalDataSource @Inject constructor(
    private val dashboardDao: DashboardDao
) {

    suspend fun saveDashboard(
        dto: DashboardResponseDto
    ) {
        val dashboardEntity =
            DashboardLocalMapper.toDashboardEntity(dto)

        val transactionEntities =
            DashboardLocalMapper.toTransactionEntities(dto)

        dashboardDao.replaceDashboard(
            dashboard = dashboardEntity,
            transactions = transactionEntities
        )
    }

    suspend fun getDashboard(): Dashboard? {
        val dashboardEntity =
            dashboardDao.getDashboard()
                ?: return null

        val transactionEntities =
            dashboardDao.getLatestTransactions()

        return DashboardLocalMapper.toDomain(
            dashboard = dashboardEntity,
            transactions = transactionEntities
        )
    }

    suspend fun clear() {
        dashboardDao.clearAll()
    }
}