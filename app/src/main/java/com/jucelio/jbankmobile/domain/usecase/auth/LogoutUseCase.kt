package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.repository.AuthRepository
import com.jucelio.jbankmobile.domain.usecase.BaseUseCase

class LogoutUseCase(
    private val repository: AuthRepository
) : BaseUseCase<Unit, Unit> {

    override suspend fun invoke(
        param: Unit
    ) {
        repository.logout()
    }
}