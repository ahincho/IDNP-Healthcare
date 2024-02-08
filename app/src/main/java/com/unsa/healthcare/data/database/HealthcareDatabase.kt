package com.unsa.healthcare.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unsa.healthcare.core.Converters
import com.unsa.healthcare.data.database.daos.CategoryDao
import com.unsa.healthcare.data.database.daos.MedicineDao
import com.unsa.healthcare.data.database.daos.ReminderDao
import com.unsa.healthcare.data.database.daos.WorkoutDao
import com.unsa.healthcare.data.database.entities.CategoryEntity
import com.unsa.healthcare.data.database.entities.MedicineEntity
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.database.entities.WorkoutEntity

@Database(entities = [MedicineEntity::class, CategoryEntity::class, ReminderEntity::class, WorkoutEntity::class], version = 5, exportSchema = true)
@TypeConverters(Converters::class)
abstract class HealthcareDatabase: RoomDatabase() {
    abstract fun getMedicineDao(): MedicineDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getReminderDao(): ReminderDao
    abstract fun getWorkoutDao(): WorkoutDao
}