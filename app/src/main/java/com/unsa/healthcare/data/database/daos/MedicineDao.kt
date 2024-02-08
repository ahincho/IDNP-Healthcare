package com.unsa.healthcare.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unsa.healthcare.data.database.entities.MedicineEntity

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines")
    fun getAllMedicines(): List<MedicineEntity>
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicine(medicineEntity: MedicineEntity)
}