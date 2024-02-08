package com.unsa.healthcare.domain.auth.login

import androidx.datastore.preferences.core.Preferences
import com.unsa.healthcare.data.repositories.PreferencesRepository
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor (
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>): T? {
        return preferencesRepository.getPreference(key)
    }
}