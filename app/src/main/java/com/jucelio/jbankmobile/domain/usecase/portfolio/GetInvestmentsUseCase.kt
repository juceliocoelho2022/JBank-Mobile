package com.jucelio.jbankmobile.domain.usecase.portfolio

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Investment
import com.jucelio.jbankmobile.domain.repository.PortfolioRepository
import javax.inject.Inject

class GetInvestmentsUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {

    suspend operator fun invoke(): AppResult<List<Investment>> {
        return repository.getInvestments()
    }
}
