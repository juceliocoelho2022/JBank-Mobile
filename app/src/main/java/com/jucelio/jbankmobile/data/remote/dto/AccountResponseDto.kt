package com.jucelio.jbankmobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class AccountResponseDto(
    val id: Long = 0,

    @SerializedName(
        value = "number",
        alternate = ["accountNumber", "account_number"]
    )
    val number: String = "",

    @SerializedName(
        value = "type",
        alternate = ["accountType", "account_type"]
    )
    val type: String = "Conta Corrente",

    @SerializedName(
        value = "balance",
        alternate = ["saldo"]
    )
    val balance: BigDecimal = BigDecimal.ZERO,

    @SerializedName(
        value = "active",
        alternate = ["isActive"]
    )
    val active: Boolean = true,

    val customerId: Long? = null
)