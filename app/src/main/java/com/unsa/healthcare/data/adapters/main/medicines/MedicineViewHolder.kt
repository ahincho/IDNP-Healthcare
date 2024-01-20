package com.unsa.healthcare.data.adapters.main.medicines

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.databinding.ItemMedicineBinding

class MedicineViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemMedicineBinding.bind(view)
    fun renderMedicine(medicineResponse: MedicineResponse) {
        binding.itemMedicineId.text = medicineResponse.id.toString()
        binding.itemMedicineName.text = medicineResponse.name
        binding.itemMedicineCategory.text = medicineResponse.category
    }
}