package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.data.network.clients.AuthApiClient
import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.network.dtos.auth.login.LoginResponse
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val authApiClient: AuthApiClient
) {
    suspend fun loginFromApi(loginRequest: LoginRequest): LoginResponse? {
        return withContext(Dispatchers.IO) {
            val response = authApiClient.login(loginRequest)
            response.body()
        }
    }
    suspend fun registerFromApi(registerRequest: RegisterRequest): RegisterResponse? {
        return withContext(Dispatchers.IO) {
            val response = authApiClient.register(registerRequest)
            response.body()
        }
    }
}