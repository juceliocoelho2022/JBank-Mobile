package com.jucelio.jbankmobile.core.session

import com.jucelio.jbankmobile.data.local.session.SessionStorage
import com.jucelio.jbankmobile.data.local.session.UserSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centraliza as operações relacionadas à sessão autenticada.
 *
 * ViewModels e repositories devem utilizar esta classe em vez de
 * acessar diretamente o DataStore.
 */
@Singleton
class SessionManager @Inject constructor(
    private val sessionStorage: SessionStorage
) {

    /**
     * Fluxo observável da sessão atual.
     */
    val sessionFlow: Flow<UserSession?>
        get() = sessionStorage.sessionFlow

    /**
     * Retorna a sessão salva no dispositivo.
     */
    suspend fun getCurrentSession(): UserSession? {
        return sessionStorage.getSession()
    }

    /**
     * Salva uma sessão completa.
     */
    suspend fun saveSession(
        session: UserSession
    ) {
        require(session.accessToken.isNotBlank()) {
            "O token de acesso não pode estar vazio."
        }

        sessionStorage.saveSession(session)
    }

    /**
     * Salva uma sessão quando a API retorna somente o JWT.
     */
    suspend fun saveAccessToken(
        accessToken: String
    ) {
        require(accessToken.isNotBlank()) {
            "O token de acesso não pode estar vazio."
        }

        sessionStorage.saveSession(
            UserSession(
                accessToken = accessToken
            )
        )
    }

    /**
     * Informa se existe uma sessão válida.
     */
    suspend fun isAuthenticated(): Boolean {
        return sessionStorage.hasValidSession()
    }

    /**
     * Remove completamente a sessão local.
     */
    suspend fun logout() {
        sessionStorage.clearSession()
    }
}