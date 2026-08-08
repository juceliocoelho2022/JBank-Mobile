package com.jucelio.jbankmobile.core.network

/**
 * Representa o resultado de uma operação realizada
 * contra uma fonte de dados, como uma API REST.
 */
sealed interface ApiResult<out T> {

    data class Success<T>(
        val data: T
    ) : ApiResult<T>

    data class Error(
        val type: NetworkError,
        val message: String,
        val code: Int? = null
    ) : ApiResult<Nothing>
}