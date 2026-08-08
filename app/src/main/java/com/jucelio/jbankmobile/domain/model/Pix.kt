package com.jucelio.jbankmobile.domain.model

import java.math.BigDecimal

data class Pix(
    val id: Long,
    val key: String,
    val receiver: String,
    val amount: BigDecimal,
    val date: String
)