package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val api: JBankApi
) {

    suspend fun loadDashboard(): Result<DashboardResponseDto> {
        return runCatching {
            api.getDashboard()
        }
    }
}