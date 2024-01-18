package com.unsa.healthcare.data.database

interface DataStorePreferences {
    suspend fun saveJwtToken(jwt: String)
    suspend fun getJwtToken(): String?
}