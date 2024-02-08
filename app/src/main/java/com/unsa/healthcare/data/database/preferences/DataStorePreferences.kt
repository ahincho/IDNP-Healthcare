package com.unsa.healthcare.data.database.preferences

import androidx.datastore.preferences.core.Preferences

interface DataStorePreferences {
    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T)
    suspend fun <T> getPreference(key: Preferences.Key<T>): T?
    suspend fun <T> deletePreference(key: Preferences.Key<T>)
}