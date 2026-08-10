package com.jucelio.jbankmobile.data.mapper

import com.jucelio.jbankmobile.data.local.entity.InvestmentEntity
import com.jucelio.jbankmobile.data.remote.dto.InvestmentResponseDto
import com.jucelio.jbankmobile.domain.model.Investment

fun InvestmentResponseDto.toDomain(): Investment {
    return Investment(
        id = id,
        name = name,
        type = type,
        investedAmount = investedAmount,
        currentValue = currentValue,
        profitability = profitability
    )
}

fun InvestmentResponseDto.toEntity(): InvestmentEntity {
    return InvestmentEntity(
        id = id,
        name = name,
        type = type,
        investedAmount = investedAmount,
        currentValue = currentValue,
        profitability = profitability
    )
}
