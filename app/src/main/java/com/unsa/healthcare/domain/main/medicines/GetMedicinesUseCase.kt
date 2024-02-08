package com.unsa.healthcare.domain.main.medicines

import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.data.repositories.MedicineRepository
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor (
    private val medicineRepository: MedicineRepository
) {
    suspend operator fun invoke(): List<Medicine> {
        return medicineRepository.getMedicinesFromDatabase()
    }
}