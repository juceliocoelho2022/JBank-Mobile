package com.jucelio.jbankmobile.fakes

import com.jucelio.jbankmobile.domain.model.Notification
import com.jucelio.jbankmobile.fixtures.notification.NotificationFixtures

class FakeNotificationRepository {

    var notifications: List<Notification> =
        NotificationFixtures.notificationList()

    var shouldFail: Boolean = false

    var errorMessage: String =
        "Erro ao carregar notificações"

    var getNotificationsCalls: Int = 0
        private set

    fun returnNotifications(
        value: List<Notification>
    ) {
        notifications = value
        shouldFail = false
    }

    fun returnFailure(
        message: String = "Erro ao carregar notificações"
    ) {
        shouldFail = true
        errorMessage = message
    }

    fun registerGetNotificationsCall() {
        getNotificationsCalls++
    }

    fun reset() {
        notifications =
            NotificationFixtures.notificationList()

        shouldFail = false
        errorMessage = "Erro ao carregar notificações"
        getNotificationsCalls = 0
    }
}