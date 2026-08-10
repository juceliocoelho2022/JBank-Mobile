package com.jucelio.jbankmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "investments")
data class InvestmentEntity(

    @PrimaryKey
    val id: Long,

    val name: String,

    val type: String,

    val investedAmount: BigDecimal,

    val currentValue: BigDecimal,

    val profitability: BigDecimal
)
