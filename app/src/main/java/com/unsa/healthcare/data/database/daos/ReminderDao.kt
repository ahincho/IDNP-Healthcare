package com.unsa.healthcare.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unsa.healthcare.data.database.entities.ReminderEntity

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): List<ReminderEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminderEntity: ReminderEntity)
    @Query("DELETE FROM reminders WHERE id = :id")
    fun deleteReminder(id: Int)
}