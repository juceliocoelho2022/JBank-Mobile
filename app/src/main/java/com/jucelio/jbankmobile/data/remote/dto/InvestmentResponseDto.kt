package com.jucelio.jbankmobile.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class InvestmentResponseDto(
    val id: Long = 0,

    @SerializedName(
        value = "name",
        alternate = ["assetName", "asset_name"]
    )
    val name: String = "",

    @SerializedName(
        value = "type",
        alternate = ["assetType", "asset_type"]
    )
    val type: String = "RENDA_FIXA",

    @SerializedName(
        value = "investedAmount",
        alternate = ["invested_amount", "appliedAmount"]
    )
    val investedAmount: BigDecimal = BigDecimal.ZERO,

    @SerializedName(
        value = "currentValue",
        alternate = ["current_value", "balance"]
    )
    val currentValue: BigDecimal = BigDecimal.ZERO,

    @SerializedName(
        value = "profitability",
        alternate = ["profitabilityPercentage", "yield"]
    )
    val profitability: BigDecimal = BigDecimal.ZERO
)
