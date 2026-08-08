package com.jucelio.jbankmobile.fakes

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures

class FakeDashboardRepository : DashboardRepository {

    var result: AppResult<Dashboard> = AppResult.Success(
        DashboardFixtures.dashboard()
    )

    var getDashboardCalls: Int = 0
        private set

    override suspend fun getDashboard(): AppResult<Dashboard> {
        getDashboardCalls++
        return result
    }

    fun returnSuccess(
        dashboard: Dashboard = DashboardFixtures.dashboard()
    ) {
        result = AppResult.Success(dashboard)
    }

    fun returnFailure(
        message: String = "Erro ao carregar dashboard"
    ) {
        result = AppResult.Failure(message)
    }

    fun reset() {
        getDashboardCalls = 0
        result = AppResult.Success(
            DashboardFixtures.dashboard()
        )
    }
}