package com.unsa.healthcare.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unsa.healthcare.core.Constants.Companion.REMINDER_STATUS_MISSED
import com.unsa.healthcare.core.Constants.Companion.REMINDER_STATUS_TAKEN
import com.unsa.healthcare.data.database.entities.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders ORDER BY id DESC")
    fun getAllReminders(): MutableList<ReminderEntity>
    @Query("SELECT COUNT(*) FROM reminders")
    fun getRemindersCount(): Int
    @Query("SELECT COUNT(*) FROM reminders WHERE status = '$REMINDER_STATUS_MISSED'")
    fun getRemindersMissedCount(): Int
    @Query("SELECT COUNT(*) FROM reminders WHERE status = '$REMINDER_STATUS_TAKEN'")
    fun getRemindersTakenCount(): Int
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminderEntity: ReminderEntity)
    @Transaction
    @Query("UPDATE reminders SET status = '$REMINDER_STATUS_MISSED' WHERE id = :id")
    fun updateReminderStatusToMissed(id: Int)
    @Transaction
    @Query("UPDATE reminders SET status = '$REMINDER_STATUS_TAKEN' WHERE id = :id")
    fun updateReminderStatusToTaken(id: Int)
    @Transaction
    @Query("DELETE FROM reminders WHERE id = :id")
    fun deleteReminderById(id: Int)
}