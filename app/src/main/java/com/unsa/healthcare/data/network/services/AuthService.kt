package com.unsa.healthcare.data.network.services

import com.unsa.healthcare.data.network.dtos.auth.login.*
import com.unsa.healthcare.data.network.dtos.auth.register.*
import com.unsa.healthcare.data.network.clients.AuthApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor (
    private val authApiClient: AuthApiClient
) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return withContext(Dispatchers.IO) {
            val response = authApiClient.login(loginRequest)
            response.body()
        }
    }
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return withContext(Dispatchers.IO) {
            val response = authApiClient.register(registerRequest)
            response.body()
        }
    }
}