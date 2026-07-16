package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.DashboardRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val remoteDataSource: DashboardRemoteDataSource
) : DashboardRepository {

    override suspend fun getDashboard(): AppResult<Dashboard> {
        return when (
            val result = safeApiCall {
                remoteDataSource.getDashboard()
            }
        ) {
            is ApiResult.Success -> {
                AppResult.Success(
                    data = result.data.toDomain()
                )
            }

            is ApiResult.Error -> {
                AppResult.Failure(
                    message = result.message,
                    code = result.code
                )
            }
        }
    }
}