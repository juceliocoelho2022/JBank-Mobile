package com.jucelio.jbankmobile.core.network

/**
 * Categorias de falhas que podem ocorrer
 * durante uma comunicação com a API.
 */
enum class NetworkError {
    NO_INTERNET,
    TIMEOUT,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    SERVER_ERROR,
    CLIENT_ERROR,
    UNKNOWN
}