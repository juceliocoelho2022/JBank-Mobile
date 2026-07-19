package com.jucelio.jbankmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "dashboard_transactions")
data class DashboardTransactionEntity(

    @PrimaryKey
    val position: Int,

    val remoteId: Long?,

    val type: String?,

    val amount: BigDecimal,

    val description: String?,

    val sourceAccountId: Long?,

    val targetAccountId: Long?,

    val createdAt: String?
)