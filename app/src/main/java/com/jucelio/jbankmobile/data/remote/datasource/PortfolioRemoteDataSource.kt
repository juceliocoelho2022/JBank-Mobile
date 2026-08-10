package com.jucelio.jbankmobile.data.remote.datasource

import com.jucelio.jbankmobile.data.remote.JBankApi
import com.jucelio.jbankmobile.data.remote.dto.InvestmentResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRemoteDataSource @Inject constructor(
    private val api: JBankApi
) {

    suspend fun getInvestments(): List<InvestmentResponseDto> {
        return api.getPortfolio()
    }
}
