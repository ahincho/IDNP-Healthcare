package com.unsa.healthcare.data.adapters.main.medicines

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.R
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.ui.view.main.medicines.MedicineDetailActivity

class MedicineAdapter (
    private var medicines: List<MedicineResponse>
): RecyclerView.Adapter<MedicineViewHolder>() {
    companion object {
        const val MEDICINE_ID = "MEDICINE_ID"
        const val MEDICINE_NAME = "MEDICINE_NAME"
        const val MEDICINE_CATEGORY = "MEDICINE_CATEGORY"
        const val MEDICINE_DESCRIPTION = "MEDICINE_DESCRIPTION"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MedicineViewHolder(layoutInflater.inflate(R.layout.item_medicine, parent, false))
    }
    override fun getItemCount(): Int {
        return medicines.size
    }
    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicineResponse = medicines[position]
        holder.itemView.findViewById<CardView>(R.id.medicineCardView).setOnClickListener {
            val intent = Intent(it.context, MedicineDetailActivity::class.java)
            intent.putExtra(MEDICINE_ID, medicines[position].id)
            intent.putExtra(MEDICINE_NAME, medicines[position].name)
            intent.putExtra(MEDICINE_CATEGORY, medicines[position].category)
            intent.putExtra(MEDICINE_DESCRIPTION, medicines[position].description)
            it.context.startActivity(intent)
        }
        holder.renderMedicine(medicineResponse)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateMedicines(suppliers: List<MedicineResponse>?) {
        this.medicines = suppliers ?: emptyList()
        notifyDataSetChanged()
    }
}