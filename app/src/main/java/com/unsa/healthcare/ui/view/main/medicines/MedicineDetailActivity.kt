package com.unsa.healthcare.ui.view.main.medicines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unsa.healthcare.data.adapters.main.medicines.MedicineAdapter.Companion.MEDICINE_CATEGORY
import com.unsa.healthcare.data.adapters.main.medicines.MedicineAdapter.Companion.MEDICINE_DESCRIPTION
import com.unsa.healthcare.data.adapters.main.medicines.MedicineAdapter.Companion.MEDICINE_ID
import com.unsa.healthcare.data.adapters.main.medicines.MedicineAdapter.Companion.MEDICINE_NAME
import com.unsa.healthcare.databinding.ActivityMedicineDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicineDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineDetailBinding
    private var id: Int? = -1
    private lateinit var name: String
    private lateinit var category: String
    private lateinit var description: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDetailBinding.inflate(layoutInflater)
        id = intent?.getIntExtra(MEDICINE_ID, -1) ?: -1
        name = intent?.getStringExtra(MEDICINE_NAME) ?: "Not Found"
        category = intent?.getStringExtra(MEDICINE_CATEGORY) ?: "Not Found"
        description = intent?.getStringExtra(MEDICINE_DESCRIPTION) ?: "Not Found"
        loadSupplierInfo()
        setContentView(binding.root)
    }
    private fun loadSupplierInfo() {
        binding.detailMedicineName.text = name
        binding.detailMedicineCategory.text = category
        binding.detailMedicineDescription.text = description
    }
}