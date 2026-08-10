package com.jucelio.jbankmobile.domain.repository

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Portfolio

interface PortfolioRepository {

    suspend fun getPortfolio(): AppResult<Portfolio>
}
