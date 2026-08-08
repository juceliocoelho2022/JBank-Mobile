package com.jucelio.jbankmobile.domain.model

/**
 * Modelo de domínio de uma notificação.
 *
 * Não possui dependência de Retrofit, Gson ou Android.
 */
data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val type: String,
    val isRead: Boolean,
    val createdAt: String
)