package com.jucelio.jbankmobile.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "accounts")
data class AccountEntity(

    @PrimaryKey
    val id: Long,

    val number: String,

    val type: String,

    val balance: BigDecimal,

    val active: Boolean,

    val customerId: Long?
)