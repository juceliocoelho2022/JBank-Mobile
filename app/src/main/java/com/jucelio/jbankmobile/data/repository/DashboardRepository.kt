package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto

class DashboardRepository(
    private val api: JBankApi
) {
    suspend fun loadDashboard(): Result<DashboardResponseDto> =
        runCatching { api.getDashboard() }
}
