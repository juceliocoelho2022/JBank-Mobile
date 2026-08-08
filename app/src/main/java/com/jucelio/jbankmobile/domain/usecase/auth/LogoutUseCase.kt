package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Encerra a sessão autenticada do usuário.
 */
class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): AppResult<Unit> {
        return repository.logout()
    }
}