package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.network.dtos.auth.login.LoginResponse
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterResponse
import com.unsa.healthcare.data.network.services.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val authService: AuthService
) {
    suspend fun attemptLogin(loginRequest: LoginRequest): LoginResponse? {
        return authService.login(loginRequest)
    }
    suspend fun attemptRegister(registerRequest: RegisterRequest): RegisterResponse? {
        return authService.register(registerRequest)
    }
}