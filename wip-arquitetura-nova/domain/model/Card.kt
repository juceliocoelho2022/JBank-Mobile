package com.jucelio.jbankmobile.domain.model

data class Card(
    val id: Long,
    val lastNumbers: String,
    val holder: String,
    val limit: Double,
    val availableLimit: Double,
    val blocked: Boolean
)