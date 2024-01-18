package com.unsa.healthcare.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unsa.healthcare.data.database.daos.ReminderDao
import com.unsa.healthcare.data.database.entities.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = true)
abstract class HealthcareDatabase: RoomDatabase() {
    abstract fun getReminderDao(): ReminderDao
}