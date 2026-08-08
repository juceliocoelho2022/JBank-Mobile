package com.jucelio.jbankmobile.core.common

sealed interface Result<out T> {

    data class Success<T>(
        val data: T
    ) : Result<T>

    data class Error(
        val throwable: Throwable,
        val message: String = throwable.message ?: "Erro desconhecido"
    ) : Result<Nothing>

    data object Loading : Result<Nothing>

}