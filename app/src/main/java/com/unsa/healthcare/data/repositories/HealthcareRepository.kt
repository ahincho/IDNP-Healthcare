package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.data.database.daos.ReminderDao
import com.unsa.healthcare.data.database.entities.ReminderEntity
import javax.inject.Inject

class HealthcareRepository @Inject constructor (
    private val reminderDao: ReminderDao
) {
    suspend fun getAllReminders(): MutableList<ReminderEntity> {
        return reminderDao.getAllReminders()
    }
    suspend fun getReminderById(id: Int): ReminderEntity {
        return reminderDao.getReminderById(id)
    }
    suspend fun insertReminder(reminderEntity: ReminderEntity) {
        reminderDao.insertReminder(reminderEntity)
    }
    suspend fun deleteReminder(id: Int) {
        reminderDao.deleteReminderById(id)
    }
}