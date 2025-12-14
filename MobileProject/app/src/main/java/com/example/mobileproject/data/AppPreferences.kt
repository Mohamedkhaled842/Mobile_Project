package com.example.mobileproject.data


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class AppPreferences(private val context: Context) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    val darkModeFlow = context.dataStore.data.map { prefs ->
        prefs[DARK_MODE] ?: false
    }

    suspend fun toggleDarkMode(currentValue: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE] = !currentValue
        }
    }
}
