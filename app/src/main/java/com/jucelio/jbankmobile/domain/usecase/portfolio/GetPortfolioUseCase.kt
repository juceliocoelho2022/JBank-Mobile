package com.jucelio.jbankmobile.domain.usecase.portfolio

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Portfolio
import com.jucelio.jbankmobile.domain.repository.PortfolioRepository
import javax.inject.Inject

/**
 * Recupera o portfólio de investimentos consolidado do usuário.
 */
class GetPortfolioUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {

    suspend operator fun invoke(): AppResult<Portfolio> {
        return repository.getPortfolio()
    }
}
