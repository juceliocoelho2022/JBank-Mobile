package com.jucelio.jbankmobile.domain.model

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val type: String,
    val read: Boolean,
    val createdAt: String
)