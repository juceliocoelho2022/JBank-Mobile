package com.jucelio.jbankmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representação local de uma notificação.
 */
@Entity(tableName = "notifications")
data class NotificationEntity(

    @PrimaryKey
    val id: Long,

    val title: String,

    val message: String,

    val type: String,

    val isRead: Boolean,

    val createdAt: String
)