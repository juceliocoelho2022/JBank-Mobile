package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.Card

interface CardRepository {

    suspend fun getCards(): List<Card>

    suspend fun blockCard(
        id: Long
    )

    suspend fun unblockCard(
        id: Long
    )
}