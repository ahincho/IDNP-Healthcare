package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.data.repositories.ReminderRepository
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor (
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(): MutableList<Reminder> {
        return reminderRepository.getRemindersFromDatabase()
    }
}