package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.core.network.ApiResult
import com.jucelio.jbankmobile.core.network.safeApiCall
import com.jucelio.jbankmobile.data.local.datasource.NotificationLocalDataSource
import com.jucelio.jbankmobile.data.local.mapper.NotificationLocalMapper
import com.jucelio.jbankmobile.data.mapper.toDomain
import com.jucelio.jbankmobile.data.remote.datasource.NotificationRemoteDataSource
import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource,
    private val localDataSource: NotificationLocalDataSource
) : NotificationRepository {

    override suspend fun getNotifications():
            AppResult<List<Notification>> {

        val remoteResult = safeApiCall {
            remoteDataSource.getNotifications()
        }

        return when (remoteResult) {
            is ApiResult.Success -> {
                val notifications = remoteResult.data.map { dto ->
                    dto.toDomain()
                }

                localDataSource.saveAll(
                    notifications.map { notification ->
                        NotificationLocalMapper.toEntity(notification)
                    }
                )

                AppResult.Success(data = notifications)
            }

            is ApiResult.Error -> {
                val cachedNotifications = localDataSource
                    .getNotifications()
                    .map { entity ->
                        NotificationLocalMapper.toDomain(entity)
                    }

                if (cachedNotifications.isNotEmpty()) {
                    AppResult.Success(data = cachedNotifications)
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