package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.local.datasource.DashboardLocalDataSource
import com.jucelio.jbankmobile.data.remote.datasource.DashboardRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val remoteDataSource: DashboardRemoteDataSource,
    private val localDataSource: DashboardLocalDataSource
) : DashboardRepository {

    override suspend fun getDashboard(): AppResult<Dashboard> {
        return when (
            val remoteResult = safeApiCall {
                remoteDataSource.getDashboard()
            }
        ) {
            is ApiResult.Success -> handleRemoteSuccess(
                dashboardDto = remoteResult.data
            )

            is ApiResult.Error -> handleRemoteError(
                message = remoteResult.message,
                code = remoteResult.code
            )
        }
    }

    private suspend fun handleRemoteSuccess(
        dashboardDto: com.jucelio.jbankmobile.data.remote.dto.DashboardResponseDto
    ): AppResult<Dashboard> {
        localDataSource.saveDashboard(
            dto = dashboardDto
        )

        return getCachedDashboardOrFailure(
            fallbackMessage = "Não foi possível carregar o dashboard."
        )
    }

    private suspend fun handleRemoteError(
        message: String,
        code: Int?
    ): AppResult<Dashboard> {
        return getCachedDashboardOrFailure(
            fallbackMessage = message,
            fallbackCode = code
        )
    }

    private suspend fun getCachedDashboardOrFailure(
        fallbackMessage: String,
        fallbackCode: Int? = null
    ): AppResult<Dashboard> {
        val cachedDashboard = localDataSource.getDashboard()

        return if (cachedDashboard != null) {
            AppResult.Success(
                data = cachedDashboard
            )
        } else {
            AppResult.Failure(
                message = fallbackMessage,
                code = fallbackCode
            )
        }
    }
}