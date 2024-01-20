package com.unsa.healthcare.data.network.services

import com.unsa.healthcare.data.network.clients.MedicineApiClient
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicineService @Inject constructor (
    private val medicineApiClient: MedicineApiClient
) {
    suspend fun getAllMedicines(): MutableList<MedicineResponse>? {
        return withContext(Dispatchers.IO) {
            val response = medicineApiClient.getAll()
            response.body()
        }
    }
    suspend fun getMedicineById(id: Int): MedicineResponse? {
        return withContext(Dispatchers.IO) {
            val response = medicineApiClient.getById(id)
            response.body()
        }
    }
}