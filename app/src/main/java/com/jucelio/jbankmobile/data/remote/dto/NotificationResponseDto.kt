package com.jucelio.jbankmobile.data.remote.dto

data class NotificationResponseDto(
    val id: Long = 0,
    val title: String = "Notificação",
    val message: String = "",
    val type: String = "INFO",
    val read: Boolean = false,
    val createdAt: String? = null
)