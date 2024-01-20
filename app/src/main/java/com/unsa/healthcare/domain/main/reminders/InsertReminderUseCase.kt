package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.repositories.HealthcareRepository
import javax.inject.Inject

class InsertReminderUseCase @Inject constructor (
    private val healthcareRepository: HealthcareRepository
) {
    suspend operator fun invoke(reminderEntity: ReminderEntity) {
        healthcareRepository.insertReminder(reminderEntity)
    }
}