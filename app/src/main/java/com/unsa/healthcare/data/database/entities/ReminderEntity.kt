package com.unsa.healthcare.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unsa.healthcare.core.Constants.Companion.REMINDERS_TABLE
import com.unsa.healthcare.core.Constants.Companion.REMINDER_DATE
import com.unsa.healthcare.core.Constants.Companion.REMINDER_ID
import com.unsa.healthcare.core.Constants.Companion.REMINDER_MEDICINE

@Entity(tableName = REMINDERS_TABLE)
data class ReminderEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = REMINDER_ID) val id: Int = 0,
    @ColumnInfo(name = REMINDER_MEDICINE) val medicine: String,
    @ColumnInfo(name = REMINDER_DATE) val date: String
)