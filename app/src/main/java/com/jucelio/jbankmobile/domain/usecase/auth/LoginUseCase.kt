package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.core.common.Result
import com.jucelio.jbankmobile.domain.model.User
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import com.jucelio.jbankmobile.domain.usecase.BaseUseCase
import jakarta.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) : BaseUseCase<LoginUseCase.Params, Result<User>> {

    data class Params(
        val email: String,
        val password: String
    )

    override suspend fun invoke(
        param: Params
    ): Result<User> {

        return repository.login(
            param.email,
            param.password
        )

    }

}