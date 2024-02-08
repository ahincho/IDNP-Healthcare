package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.core.toDatabase
import com.unsa.healthcare.core.toDomain
import com.unsa.healthcare.data.database.daos.ReminderDao
import com.unsa.healthcare.data.models.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReminderRepository @Inject constructor (
    private val reminderDao: ReminderDao
) {
    suspend fun getRemindersFromDatabase(): MutableList<Reminder> {
        return withContext(Dispatchers.IO) {
            reminderDao.getAllReminders().map { it.toDomain() } .toMutableList()
        }
    }
    suspend fun insertReminderOnDatabase(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            reminderDao.insertReminder(reminder.toDatabase())
        }
    }
    suspend fun updateReminderStatusToMissedOnDatabase(id: Int) {
        return withContext(Dispatchers.IO) {
            reminderDao.updateReminderStatusToMissed(id)
        }
    }
    suspend fun updateReminderStatusToTaken(id: Int) {
        return withContext(Dispatchers.IO) {
            reminderDao.updateReminderStatusToTaken(id)
        }
    }
    suspend fun deleteReminderFromDatabase(id: Int) {
        return withContext(Dispatchers.IO) {
            reminderDao.deleteReminderById(id)
        }
    }
}