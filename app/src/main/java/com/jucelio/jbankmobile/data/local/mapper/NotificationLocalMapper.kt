package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.data.local.entity.NotificationEntity
import com.jucelio.jbankmobile.domain.model.Notification

object NotificationLocalMapper {

    fun toEntity(notification: Notification): NotificationEntity {

        return NotificationEntity(
            id = notification.id,
            title = notification.title,
            message = notification.message,
            type = notification.type,
            isRead = notification.isRead,
            createdAt = notification.createdAt
        )
    }

    fun toDomain(entity: NotificationEntity): Notification {

        return Notification(
            id = entity.id,
            title = entity.title,
            message = entity.message,
            type = entity.type,
            isRead = entity.isRead,
            createdAt = entity.createdAt
        )
    }
}