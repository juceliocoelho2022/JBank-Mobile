package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRemoteDataSource @Inject constructor(
    private val api: JBankApi
) {

    suspend fun getNotifications():
            List<NotificationResponseDto> {

        return api.getNotifications()
    }
}