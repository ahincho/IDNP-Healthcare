package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.repositories.HealthcareRepository
import javax.inject.Inject

class DeleteReminderByIdUseCase @Inject constructor (
    private val healthcareRepository: HealthcareRepository
) {
    suspend operator fun invoke(id: Int) {
        healthcareRepository.deleteReminder(id)
    }
}