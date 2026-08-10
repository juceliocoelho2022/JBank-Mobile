package com.jucelio.jbankmobile.data.local.mapper

import com.jucelio.jbankmobile.data.local.entity.InvestmentEntity
import com.jucelio.jbankmobile.domain.model.Investment

object InvestmentLocalMapper {

    fun toEntity(investment: Investment): InvestmentEntity {
        return InvestmentEntity(
            id = investment.id,
            name = investment.name,
            type = investment.type,
            investedAmount = investment.investedAmount,
            currentValue = investment.currentValue,
            profitability = investment.profitability
        )
    }

    fun toDomain(entity: InvestmentEntity): Investment {
        return Investment(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            investedAmount = entity.investedAmount,
            currentValue = entity.currentValue,
            profitability = entity.profitability
        )
    }
}
