package com.example.mytestapplication.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "auth_prefs"
)

class TokenStore private constructor(
    private val appContext: Context,
) {

    private val dataStore = appContext.authDataStore

    @Volatile
    var cachedToken: String? = null

    suspend fun loadToken(): String? {
        val token = dataStore.data.first()[TOKEN_KEY]
        cachedToken = token
        return token
    }

    suspend fun saveToken(token: String) {
        cachedToken = token
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        cachedToken = null
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")

        @Volatile
        private var instance: TokenStore? = null

        fun getInstance(context: Context): TokenStore =
            instance ?: synchronized(this) {
                instance ?: TokenStore(context.applicationContext).also { instance = it }
            }
    }
}
