package com.unsa.healthcare.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unsa.healthcare.data.database.entities.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): MutableList<ReminderEntity>
    @Query("SELECT * FROM reminders WHERE id = :id")
    fun getReminderById(id: Int): ReminderEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminderEntity: ReminderEntity)
    @Query("DELETE FROM reminders WHERE id = :id")
    fun deleteReminderById(id: Int)
}