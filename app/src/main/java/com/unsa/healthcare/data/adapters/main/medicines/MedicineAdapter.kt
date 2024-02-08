package com.unsa.healthcare.data.adapters.main.medicines

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.R
import com.unsa.healthcare.data.models.Medicine

class MedicineAdapter (
    private var medicines: List<Medicine>
): RecyclerView.Adapter<MedicineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MedicineViewHolder(layoutInflater.inflate(R.layout.item_medicine, parent, false))
    }
    override fun getItemCount(): Int {
        return medicines.size
    }
    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.renderMedicine(medicine)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMedicines(medicines: List<Medicine>?) {
        this.medicines = medicines ?: emptyList()
        notifyDataSetChanged()
    }
}