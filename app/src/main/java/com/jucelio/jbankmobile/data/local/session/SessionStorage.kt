package com.jucelio.jbankmobile.data.local.session

import kotlinx.coroutines.flow.Flow

/**
 * Contrato responsável pelo armazenamento da sessão autenticada.
 *
 * A interface permite trocar a implementação do armazenamento
 * sem afetar ViewModels, repositories ou casos de uso.
 */
interface SessionStorage {

    val sessionFlow: Flow<UserSession?>

    suspend fun getSession(): UserSession?

    suspend fun saveSession(
        session: UserSession
    )

    suspend fun clearSession()

    suspend fun hasValidSession(): Boolean
}