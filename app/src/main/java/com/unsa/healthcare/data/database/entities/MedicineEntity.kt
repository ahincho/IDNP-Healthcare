package com.unsa.healthcare.data.database.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unsa.healthcare.core.Constants.Companion.MEDICINES_TABLE
import com.unsa.healthcare.core.Constants.Companion.MEDICINE_CATEGORY
import com.unsa.healthcare.core.Constants.Companion.MEDICINE_DESCRIPTION
import com.unsa.healthcare.core.Constants.Companion.MEDICINE_ID
import com.unsa.healthcare.core.Constants.Companion.MEDICINE_IMAGE
import com.unsa.healthcare.core.Constants.Companion.MEDICINE_NAME

@Entity(tableName = MEDICINES_TABLE)
data class MedicineEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MEDICINE_ID) val id: Int = 0,
    @ColumnInfo(name = MEDICINE_NAME) val name: String,
    @ColumnInfo(name = MEDICINE_CATEGORY) val category: String,
    @ColumnInfo(name = MEDICINE_DESCRIPTION) val description: String,
    @ColumnInfo(name = MEDICINE_IMAGE) val image: Bitmap
)