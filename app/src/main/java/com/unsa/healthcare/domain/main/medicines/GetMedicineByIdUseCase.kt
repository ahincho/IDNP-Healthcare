package com.unsa.healthcare.domain.main.medicines

import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.data.repositories.MedicineRepository
import javax.inject.Inject

class GetMedicineByIdUseCase @Inject constructor (
    private val medicineRepository: MedicineRepository
) {
    suspend operator fun invoke(id: Int): MedicineResponse {
        return medicineRepository.attemptGetMedicineById(id) ?: MedicineResponse(1, "Error", "Error", "Error", "Error")
    }
}