package com.unsa.healthcare.domain.main.reminders

import com.unsa.healthcare.data.repositories.ReminderRepository
import javax.inject.Inject

class DeleteReminderByIdUseCase @Inject constructor (
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(id: Int) {
        reminderRepository.deleteReminderFromDatabase(id)
    }
}