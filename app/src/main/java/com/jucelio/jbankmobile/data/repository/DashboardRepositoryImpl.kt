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

        val remoteResult = safeApiCall {
            remoteDataSource.getDashboard()
        }

        return when (remoteResult) {

            is ApiResult.Success -> {
                localDataSource.saveDashboard(
                    dto = remoteResult.data
                )

                val cachedDashboard =
                    localDataSource.getDashboard()

                if (cachedDashboard != null) {
                    AppResult.Success(
                        data = cachedDashboard
                    )
                } else {
                    AppResult.Failure(
                        message = "Não foi possível carregar o dashboard."
                    )
                }
            }

            is ApiResult.Error -> {
                val cachedDashboard =
                    localDataSource.getDashboard()

                if (cachedDashboard != null) {
                    AppResult.Success(
                        data = cachedDashboard
                    )
                } else {
                    AppResult.Failure(
                        message = remoteResult.message,
                        code = remoteResult.code
                    )
                }
            }
        }
    }
}