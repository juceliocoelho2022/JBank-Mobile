package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto
import com.jucelio.jbankmobile.domain.model.Notification

fun NotificationResponseDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        message = message,
        type = type,
        isRead = read,
        createdAt = createdAt.orEmpty()
    )
}