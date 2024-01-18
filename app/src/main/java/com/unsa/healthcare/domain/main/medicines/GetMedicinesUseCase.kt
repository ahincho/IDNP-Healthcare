package com.unsa.healthcare.domain.main.medicines

import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.data.repositories.MedicineRepository
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor (
    private val medicineRepository: MedicineRepository
) {
    suspend operator fun invoke(): MutableList<MedicineResponse> {
        return medicineRepository.attemptGetMedicines() ?: mutableListOf()
    }
}