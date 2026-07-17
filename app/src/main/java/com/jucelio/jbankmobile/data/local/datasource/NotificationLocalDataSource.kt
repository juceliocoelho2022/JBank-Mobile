package com.jucelio.jbankmobile.data.local.datasource

import com.jucelio.jbankmobile.data.local.dao.NotificationDao
import com.jucelio.jbankmobile.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val dao: NotificationDao
) {

    fun observeNotifications(): Flow<List<NotificationEntity>> =
        dao.observeNotifications()

    suspend fun getNotifications(): List<NotificationEntity> =
        dao.getNotifications()

    suspend fun save(notification: NotificationEntity) =
        dao.insertNotification(notification)

    suspend fun saveAll(notifications: List<NotificationEntity>) =
        dao.replaceNotifications(notifications)

    suspend fun clear() =
        dao.clearNotifications()
}