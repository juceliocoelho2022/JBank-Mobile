package com.jucelio.jbankmobile.data.local.session

/**
 * Representa os dados persistidos da sessão autenticada.
 *
 * Tokens e identificadores são armazenados localmente,
 * mas senhas nunca devem ser persistidas.
 */
data class UserSession(
    val accessToken: String,
    val refreshToken: String? = null,
    val userId: Long? = null,
    val accountId: Long? = null,
    val userName: String? = null,
    val expiresAt: Long? = null
) {

    val isAuthenticated: Boolean
        get() = accessToken.isNotBlank()

    fun isExpired(
        currentTimeMillis: Long = System.currentTimeMillis()
    ): Boolean {
        return expiresAt?.let { expiration ->
            currentTimeMillis >= expiration
        } ?: false
    }
}