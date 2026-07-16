package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fonte remota responsável exclusivamente pelas operações
 * de dashboard realizadas pela API.
 */
@Singleton
class DashboardRemoteDataSource @Inject constructor(
    private val api: JBankApi
) {

    suspend fun getDashboard(): DashboardResponseDto {
        return api.getDashboard()
    }
}