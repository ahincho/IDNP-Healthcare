package com.unsa.healthcare.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.unsa.healthcare.core.Constants.Companion.DATA_STORE_PREFERENCES_NAME
import com.unsa.healthcare.data.database.preferences.DataStorePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.datastore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_PREFERENCES_NAME)

class PreferencesRepository(private val context: Context): DataStorePreferences {
    companion object {
        val POPULATED = booleanPreferencesKey("POPULATED")
        val USERNAME = stringPreferencesKey("USERNAME")
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val JWT = stringPreferencesKey("JWT")
    }
    override suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        withContext(Dispatchers.IO) {
            context.datastore.edit { it[key] = value }
        }
    }
    override suspend fun <T> getPreference(key: Preferences.Key<T>): T? {
        return withContext(Dispatchers.IO) {
            context.datastore.data.map { it[key] }.first()
        }
    }
    override suspend fun <T> deletePreference(key: Preferences.Key<T>) {
        withContext(Dispatchers.IO) {
            context.datastore.edit { it.remove(key) }
        }
    }
}