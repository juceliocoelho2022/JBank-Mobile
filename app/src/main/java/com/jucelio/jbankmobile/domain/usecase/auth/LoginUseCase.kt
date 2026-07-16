package com.jucelio.jbankmobile.domain.usecase.auth

import com.jucelio.jbankmobile.domain.model.AppResult
import com.jucelio.jbankmobile.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Executa a regra de negócio de autenticação.
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): AppResult<Unit> {
        val normalizedEmail = email.trim()
            .lowercase()

        if (normalizedEmail.isBlank()) {
            return AppResult.Failure(
                message = "Informe o seu e-mail."
            )
        }

        if (!EMAIL_REGEX.matches(normalizedEmail)) {
            return AppResult.Failure(
                message = "Informe um e-mail válido."
            )
        }

        if (password.isBlank()) {
            return AppResult.Failure(
                message = "Informe a sua senha."
            )
        }

        if (password.length < MINIMUM_PASSWORD_LENGTH) {
            return AppResult.Failure(
                message = "A senha deve possuir pelo menos 6 caracteres."
            )
        }

        return repository.login(
            email = normalizedEmail,
            password = password
        )
    }

    private companion object {
        const val MINIMUM_PASSWORD_LENGTH = 6

        val EMAIL_REGEX =
            Regex(
                pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            )
    }
}