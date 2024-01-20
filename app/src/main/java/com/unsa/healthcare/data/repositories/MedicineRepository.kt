package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.data.network.services.MedicineService
import javax.inject.Inject

class MedicineRepository @Inject constructor (
    private val medicineService: MedicineService
) {
    suspend fun attemptGetMedicines(): MutableList<MedicineResponse>? {
        return medicineService.getAllMedicines()
    }
    suspend fun attemptGetMedicineById(id: Int): MedicineResponse? {
        return medicineService.getMedicineById(id)
    }
}