package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.model.User
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import com.jucelio.jbankmobile.domain.usecase.BaseUseCase

class GetLoggedUserUseCase(
    private val repository: AuthRepository
) : BaseUseCase<Unit, User?> {

    override suspend fun invoke(
        param: Unit
    ): User? {

        return repository.getLoggedUser()
    }
}