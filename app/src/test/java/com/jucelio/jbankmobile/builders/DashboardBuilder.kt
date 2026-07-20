package com.jucelio.jbankmobile.builders

import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.fixtures.dashboard.DashboardFixtures

class DashboardBuilder {

    private var dashboard: Dashboard = DashboardFixtures.dashboard()

    fun withDashboard(value: Dashboard) = apply {
        dashboard = value
    }

    fun empty() = apply {
        dashboard = DashboardFixtures.emptyDashboard()
    }

    fun build(): Dashboard {
        return dashboard
    }
}