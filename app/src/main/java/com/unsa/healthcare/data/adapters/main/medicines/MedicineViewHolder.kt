package com.unsa.healthcare.data.adapters.main.medicines

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.databinding.ItemMedicineBinding

class MedicineViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemMedicineBinding.bind(view)
    fun renderMedicine(medicine: Medicine) {
        binding.itemMedicineName.text = medicine.name
        binding.itemMedicineDescription.text = medicine.description
        binding.itemMedicineImage.load(medicine.image)
    }
}