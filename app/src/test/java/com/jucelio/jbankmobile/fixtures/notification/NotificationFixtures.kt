package com.jucelio.jbankmobile.fixtures.notification

import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto
import com.jucelio.jbankmobile.domain.model.Notification

object NotificationFixtures {

    fun notification() = Notification(
        id = 1L,
        title = "PIX recebido",
        message = "Você recebeu uma transferência de R$ 500,00.",
        type = "SUCCESS",
        isRead = false,
        createdAt = "2026-07-20T10:00:00"
    )

    fun notificationResponseDto() = NotificationResponseDto(
        id = 1L,
        title = "PIX recebido",
        message = "Você recebeu uma transferência de R$ 500,00.",
        type = "SUCCESS",
        read = false,
        createdAt = "2026-07-20T10:00:00"
    )

    fun readNotification() = Notification(
        id = 2L,
        title = "Pagamento confirmado",
        message = "O pagamento da sua conta foi confirmado.",
        type = "INFO",
        isRead = true,
        createdAt = "2026-07-20T11:00:00"
    )

    fun unreadNotification() = Notification(
        id = 3L,
        title = "Nova notificação",
        message = "Você possui uma nova mensagem.",
        type = "INFO",
        isRead = false,
        createdAt = "2026-07-20T12:00:00"
    )

    fun errorNotification() = Notification(
        id = 4L,
        title = "Falha na transação",
        message = "Não foi possível concluir a operação.",
        type = "ERROR",
        isRead = false,
        createdAt = "2026-07-20T13:00:00"
    )

    fun notificationList() = listOf(
        notification(),
        readNotification(),
        unreadNotification(),
        errorNotification()
    )

    fun notificationResponseDtoList() = listOf(
        notificationResponseDto(),
        NotificationResponseDto(
            id = 2L,
            title = "Pagamento confirmado",
            message = "O pagamento da sua conta foi confirmado.",
            type = "INFO",
            read = true,
            createdAt = "2026-07-20T11:00:00"
        )
    )

    fun emptyNotificationList(): List<Notification> = emptyList()

    fun emptyNotificationResponseDtoList(): List<NotificationResponseDto> =
        emptyList()

    fun notificationWithNullDate() = NotificationResponseDto(
        id = 5L,
        title = "Notificação sem data",
        message = "Exemplo para testar createdAt nulo.",
        type = "INFO",
        read = false,
        createdAt = null
    )
}