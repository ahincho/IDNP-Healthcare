package com.unsa.healthcare.data.database.preferences

interface DataStorePreferences {
    suspend fun saveJwtToken(jwt: String)
    suspend fun getJwtToken(): String?
}