package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard

/**
 * Contrato para obtenção dos dados do dashboard.
 */
interface DashboardRepository {

    suspend fun getDashboard(): AppResult<Dashboard>
}