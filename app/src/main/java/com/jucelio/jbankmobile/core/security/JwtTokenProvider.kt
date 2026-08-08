package com.jucelio.jbankmobile.core.security

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(
    "jbank_preferences"
)

class JwtTokenProvider(
    private val context: Context
) : TokenProvider {

    companion object {

        private val TOKEN =
            stringPreferencesKey("jwt_token")

    }

    override suspend fun saveToken(
        token: String
    ) {

        context.dataStore.edit {

            it[TOKEN] = token

        }

    }

    override suspend fun getToken(): String? {

        return context.dataStore.data
            .first()[TOKEN]

    }

    override suspend fun clearToken() {

        context.dataStore.edit {

            it.remove(TOKEN)

        }

    }

}