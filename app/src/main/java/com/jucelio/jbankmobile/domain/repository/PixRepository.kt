package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.Pix
import java.math.BigDecimal

interface PixRepository {

    suspend fun sendPix(
        key: String,
        amount: BigDecimal
    ): Pix

    suspend fun getHistory(): List<Pix>
}