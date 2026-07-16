package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.remote.dto.NotificationResponseDto
import com.jucelio.jbankmobile.domain.model.Notification

fun NotificationResponseDto.toDomain(): Notification {

    return Notification(
        id = id,
        title = title,
        message = message,
        type = type,
        read = read,
        createdAt = createdAt.orEmpty()
    )
}