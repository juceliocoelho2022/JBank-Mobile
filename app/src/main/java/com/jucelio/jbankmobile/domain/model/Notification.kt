package com.jucelio.jbankmobile.domain.model

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val read: Boolean,
    val date: String
)