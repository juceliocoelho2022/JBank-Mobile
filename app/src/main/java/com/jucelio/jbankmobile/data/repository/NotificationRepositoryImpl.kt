package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.NotificationRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource
) : NotificationRepository {

    override suspend fun getNotifications():
            AppResult<List<Notification>> {

        return when (
            val result = safeApiCall {
                remoteDataSource.getNotifications()
            }
        ) {
            is ApiResult.Success -> {
                AppResult.Success(
                    data = result.data.map { dto ->
                        dto.toDomain()
                    }
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