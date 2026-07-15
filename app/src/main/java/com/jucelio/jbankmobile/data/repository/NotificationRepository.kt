package com.jucelio.jbankmobile.data.repository

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto

class NotificationRepository(
    private val api: JBankApi
) {

    suspend fun getNotifications():
            Result<List<NotificationResponseDto>> {

        return runCatching {
            api.getNotifications()
        }
    }
}