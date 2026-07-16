package com.jucelio.jbankmobile.data.local.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val SESSION_DATA_STORE_NAME = "jbank_session"

private val Context.sessionDataStore by preferencesDataStore(
    name = SESSION_DATA_STORE_NAME
)

/**
 * Persistência local da sessão autenticada utilizando DataStore.
 *
 * A implementação é gerenciada pelo Hilt e possui uma única
 * instância durante o ciclo de vida da aplicação.
 */
@Singleton
class DataStoreSessionStorage @Inject constructor(
    @ApplicationContext private val context: Context
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

            session.refreshToken?.let { value ->
                preferences[Keys.REFRESH_TOKEN] = value
            } ?: preferences.remove(Keys.REFRESH_TOKEN)

            session.userId?.let { value ->
                preferences[Keys.USER_ID] = value
            } ?: preferences.remove(Keys.USER_ID)

            session.accountId?.let { value ->
                preferences[Keys.ACCOUNT_ID] = value
            } ?: preferences.remove(Keys.ACCOUNT_ID)

            session.userName?.let { value ->
                preferences[Keys.USER_NAME] = value
            } ?: preferences.remove(Keys.USER_NAME)

            session.expiresAt?.let { value ->
                preferences[Keys.EXPIRES_AT] = value
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