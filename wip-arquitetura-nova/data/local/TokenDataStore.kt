package com.jucelio.jbankmobile.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore by preferencesDataStore(
    name = "jbank_auth"
)

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val ACCESS_TOKEN =
            stringPreferencesKey("access_token")

        val TOKEN_TYPE =
            stringPreferencesKey("token_type")

        val EXPIRES_IN =
            longPreferencesKey("expires_in")

        val IS_LOGGED_IN =
            booleanPreferencesKey("is_logged_in")
    }

    val accessToken: Flow<String?> =
        context.authDataStore.data.map { preferences ->
            preferences[Keys.ACCESS_TOKEN]
        }

    val isLoggedIn: Flow<Boolean> =
        context.authDataStore.data.map { preferences ->
            preferences[Keys.IS_LOGGED_IN] ?: false
        }

    suspend fun saveSession(
        token: String,
        tokenType: String,
        expiresIn: Long?
    ) {
        context.authDataStore.edit { preferences ->
            preferences[Keys.ACCESS_TOKEN] = token
            preferences[Keys.TOKEN_TYPE] = tokenType
            preferences[Keys.IS_LOGGED_IN] = true

            if (expiresIn != null) {
                preferences[Keys.EXPIRES_IN] = expiresIn
            } else {
                preferences.remove(Keys.EXPIRES_IN)
            }
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}