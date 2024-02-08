package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.data.repositories.ReminderRepository
import javax.inject.Inject

class InsertReminderUseCase @Inject constructor (
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        reminderRepository.insertReminderOnDatabase(reminder)
    }
}