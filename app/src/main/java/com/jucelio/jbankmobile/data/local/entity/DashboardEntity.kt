package com.jucelio.jbankmobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "dashboard")
data class DashboardEntity(

    @PrimaryKey
    val id: Int = 1,

    val totalCustomers: Long,

    val totalAccounts: Long,

    val totalTransactions: Long,

    val totalBalance: BigDecimal,

    val totalMoved: BigDecimal
)