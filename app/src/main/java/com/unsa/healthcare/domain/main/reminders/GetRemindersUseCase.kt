package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.repositories.HealthcareRepository
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor (
    private val healthcareRepository: HealthcareRepository
) {
    suspend operator fun invoke(): MutableList<ReminderEntity> {
        return healthcareRepository.getAllReminders()
    }
}