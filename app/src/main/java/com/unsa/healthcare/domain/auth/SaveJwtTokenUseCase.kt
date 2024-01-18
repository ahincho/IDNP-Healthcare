package com.unsa.healthcare.domain.auth

import com.unsa.healthcare.data.repositories.PreferencesRepository
import javax.inject.Inject

class SaveJwtTokenUseCase @Inject constructor (
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(token: String) {
        preferencesRepository.saveJwtToken(token)
    }
}