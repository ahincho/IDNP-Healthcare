package com.unsa.healthcare.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unsa.healthcare.core.Constants.Companion.WORKOUTS_TABLE
import com.unsa.healthcare.core.Constants.Companion.WORKOUT_ACTIVITY
import com.unsa.healthcare.core.Constants.Companion.WORKOUT_DATE
import com.unsa.healthcare.core.Constants.Companion.WORKOUT_DURATION
import com.unsa.healthcare.core.Constants.Companion.WORKOUT_ID

@Entity(tableName = WORKOUTS_TABLE)
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WORKOUT_ID) val id: Int,
    @ColumnInfo(name = WORKOUT_DATE) val date: String,
    @ColumnInfo(name = WORKOUT_ACTIVITY) val activity: String,
    @ColumnInfo(name = WORKOUT_DURATION) val duration: String
)