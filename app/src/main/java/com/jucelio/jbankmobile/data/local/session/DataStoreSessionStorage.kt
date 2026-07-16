package com.jucelio.jbankmobile.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val SESSION_DATA_STORE_NAME = "jbank_session"

private val Context.sessionDataStore by preferencesDataStore(
    name = SESSION_DATA_STORE_NAME
)

/**
 * Implementação do armazenamento da sessão autenticada
 * utilizando Jetpack DataStore Preferences.
 *
 * Essa classe persiste somente dados necessários para manter
 * a sessão do usuário. Senhas nunca devem ser armazenadas.
 */
class DataStoreSessionStorage(
    private val context: Context
) : SessionStorage {

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = longPreferencesKey("user_id")
        val ACCOUNT_ID = longPreferencesKey("account_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val EXPIRES_AT = longPreferencesKey("expires_at")
    }

    override val sessionFlow: Flow<UserSession?> =
        context.sessionDataStore.data.map { preferences ->

            val accessToken = preferences[Keys.ACCESS_TOKEN]

            if (accessToken.isNullOrBlank()) {
                null
            } else {
                UserSession(
                    accessToken = accessToken,
                    refreshToken = preferences[Keys.REFRESH_TOKEN],
                    userId = preferences[Keys.USER_ID],
                    accountId = preferences[Keys.ACCOUNT_ID],
                    userName = preferences[Keys.USER_NAME],
                    expiresAt = preferences[Keys.EXPIRES_AT]
                )
            }
        }

    override suspend fun getSession(): UserSession? {
        return sessionFlow.first()
    }

    override suspend fun saveSession(
        session: UserSession
    ) {
        context.sessionDataStore.edit { preferences ->
            preferences[Keys.ACCESS_TOKEN] = session.accessToken

            session.refreshToken?.let {
                preferences[Keys.REFRESH_TOKEN] = it
            } ?: preferences.remove(Keys.REFRESH_TOKEN)

            session.userId?.let {
                preferences[Keys.USER_ID] = it
            } ?: preferences.remove(Keys.USER_ID)

            session.accountId?.let {
                preferences[Keys.ACCOUNT_ID] = it
            } ?: preferences.remove(Keys.ACCOUNT_ID)

            session.userName?.let {
                preferences[Keys.USER_NAME] = it
            } ?: preferences.remove(Keys.USER_NAME)

            session.expiresAt?.let {
                preferences[Keys.EXPIRES_AT] = it
            } ?: preferences.remove(Keys.EXPIRES_AT)
        }
    }

    override suspend fun clearSession() {
        context.sessionDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun hasValidSession(): Boolean {
        val session = getSession()

        return session != null &&
                session.isAuthenticated &&
                !session.isExpired()
    }
}