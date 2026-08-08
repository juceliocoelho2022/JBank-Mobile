package com.jucelio.jbankmobile.domain.usecase.notification

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke():
            AppResult<List<Notification>> {

        return repository.getNotifications()
    }
}