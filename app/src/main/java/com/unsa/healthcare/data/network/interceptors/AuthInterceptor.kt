package com.unsa.healthcare.data.network.interceptors

import com.unsa.healthcare.data.repositories.PreferencesRepository
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.JWT
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor (
    private val preferencesRepository: PreferencesRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = runBlocking { preferencesRepository.getPreference(JWT) }
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}