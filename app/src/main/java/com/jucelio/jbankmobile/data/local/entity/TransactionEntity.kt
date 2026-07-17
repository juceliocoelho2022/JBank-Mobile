package com.jucelio.jbankmobile.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["sourceAccountId"]),
        Index(value = ["targetAccountId"])
    ]
)
data class TransactionEntity(

    @PrimaryKey
    val id: Long,

    val type: String,

    val amount: BigDecimal,

    val description: String,

    val sourceAccountId: Long?,

    val targetAccountId: Long?,

    val createdAt: String
)