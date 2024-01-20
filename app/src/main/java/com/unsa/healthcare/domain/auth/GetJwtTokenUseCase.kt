package com.unsa.healthcare.domain.auth

import com.unsa.healthcare.data.repositories.PreferencesRepository
import javax.inject.Inject

class GetJwtTokenUseCase @Inject constructor (
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): String {
        return preferencesRepository.getJwtToken()
    }
}