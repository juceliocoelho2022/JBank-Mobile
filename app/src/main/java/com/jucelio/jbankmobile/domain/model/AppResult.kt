package com.jucelio.jbankmobile.domain.model

/**
 * Representa o resultado de uma operação da camada de domínio.
 *
 * Essa estrutura não depende de Retrofit, OkHttp ou Android,
 * permitindo que os casos de uso sejam testados isoladamente.
 */
sealed interface AppResult<out T> {

    data class Success<T>(
        val data: T
    ) : AppResult<T>

    data class Failure(
        val message: String,
        val code: Int? = null
    ) : AppResult<Nothing>
}