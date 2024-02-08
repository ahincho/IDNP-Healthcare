package com.unsa.healthcare.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unsa.healthcare.core.Constants.Companion.CATEGORIES_TABLE
import com.unsa.healthcare.core.Constants.Companion.CATEGORY_ID
import com.unsa.healthcare.core.Constants.Companion.CATEGORY_NAME

@Entity(tableName = CATEGORIES_TABLE)
data class CategoryEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CATEGORY_ID) val id: Int = 0,
    @ColumnInfo(name = CATEGORY_NAME) val name: String
)