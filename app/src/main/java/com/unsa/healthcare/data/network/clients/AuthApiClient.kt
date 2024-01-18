package com.unsa.healthcare.data.network.clients

import com.unsa.healthcare.core.Constants.Companion.AUTH_ENDPOINT
import com.unsa.healthcare.data.network.dtos.auth.login.*
import com.unsa.healthcare.data.network.dtos.auth.register.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiClient {
    @POST("${AUTH_ENDPOINT}/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("${AUTH_ENDPOINT}/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}