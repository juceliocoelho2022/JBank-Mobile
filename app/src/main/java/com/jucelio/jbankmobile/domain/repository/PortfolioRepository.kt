package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment

interface PortfolioRepository {
    suspend fun getInvestments(): AppResult<List<Investment>>
}
