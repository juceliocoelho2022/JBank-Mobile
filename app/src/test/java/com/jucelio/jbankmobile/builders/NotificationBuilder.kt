package com.jucelio.jbankmobile.builders

import com.jucelio.jbankmobile.domain.model.Notification

class NotificationBuilder {

    private var id: Long = 1L
    private var title: String = "PIX recebido"
    private var message: String = "Você recebeu uma transferência PIX."
    private var type: String = "SUCCESS"
    private var isRead: Boolean = false
    private var createdAt: String = "2026-07-20T10:00:00"

    fun withId(value: Long) = apply {
        id = value
    }

    fun withTitle(value: String) = apply {
        title = value
    }

    fun withMessage(value: String) = apply {
        message = value
    }

    fun withType(value: String) = apply {
        type = value
    }

    fun withRead(value: Boolean) = apply {
        isRead = value
    }

    fun withCreatedAt(value: String) = apply {
        createdAt = value
    }

    fun asRead() = apply {
        isRead = true
    }

    fun asUnread() = apply {
        isRead = false
    }

    fun build(): Notification {
        return Notification(
            id = id,
            title = title,
            message = message,
            type = type,
            isRead = isRead,
            createdAt = createdAt
        )
    }
}