package com.unsa.healthcare.domain.auth.register

import com.unsa.healthcare.data.network.dtos.auth.register.RegisterRequest
import com.unsa.healthcare.data.network.dtos.auth.register.RegisterResponse
import com.unsa.healthcare.data.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): RegisterResponse? {
        return authRepository.registerFromApi(registerRequest)
    }
}