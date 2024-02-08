package com.unsa.healthcare.domain.auth.login

import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.network.dtos.auth.login.LoginResponse
import com.unsa.healthcare.data.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): LoginResponse? {
        return authRepository.loginFromApi(loginRequest)
    }
}