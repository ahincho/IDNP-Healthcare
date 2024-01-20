package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.repositories.HealthcareRepository
import javax.inject.Inject

class GetReminderByIdUseCase @Inject constructor (
    private val healthcareRepository: HealthcareRepository
) {
    suspend operator fun invoke(id: Int): ReminderEntity {
        return healthcareRepository.getReminderById(id)
    }
}