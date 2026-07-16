package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification

interface NotificationRepository {

    suspend fun getNotifications():
            AppResult<List<Notification>>
}