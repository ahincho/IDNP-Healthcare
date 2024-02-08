package com.unsa.healthcare.data.repositories

import android.content.Context
import com.unsa.healthcare.core.toDatabase
import com.unsa.healthcare.core.toDomain
import com.unsa.healthcare.data.database.daos.MedicineDao
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.data.network.clients.MedicineApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicineRepository @Inject constructor (
    private val medicineApiClient: MedicineApiClient,
    private val medicineDao: MedicineDao
) {
    suspend fun getMedicinesFromApi(context: Context): List<Medicine> {
        return withContext(Dispatchers.IO) {
            val response = medicineApiClient.getAllMedicines().body()
            response?.map { it.toDomain(context) } ?: emptyList()
        }
    }
    suspend fun getMedicinesFromDatabase(): List<Medicine> {
        return withContext(Dispatchers.IO) {
            medicineDao.getAllMedicines().map { it.toDomain() }
        }
    }
    suspend fun insertMedicineOnDatabase(medicine: Medicine) {
        return withContext(Dispatchers.IO) {
            medicineDao.insertMedicine(medicine.toDatabase())
        }
    }
}