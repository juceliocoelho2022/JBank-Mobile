package com.jucelio.jbankmobile.domain.usecase

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.model.Dashboard
import com.jucelio.jbankmobile.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(
    private val repository: DashboardRepository
) {

    suspend operator fun invoke(): AppResult<Dashboard> {
        return repository.getDashboard()
    }
}