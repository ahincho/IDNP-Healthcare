package com.unsa.healthcare.ui.view.main.reminders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter
import com.unsa.healthcare.databinding.ActivityReminderDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderDetailBinding
    private var id: Int? = -1
    private lateinit var medicine: String
    private lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderDetailBinding.inflate(layoutInflater)
        id = intent?.getIntExtra(ReminderAdapter.REMINDER_ID, -1) ?: -1
        medicine = intent?.getStringExtra(ReminderAdapter.REMINDER_MEDICINE) ?: "Not Found"
        date = intent?.getStringExtra(ReminderAdapter.REMINDER_DATE) ?: "Not Found"
        loadSupplierInfo()
        setContentView(binding.root)
    }
    private fun loadSupplierInfo() {
        binding.detailReminderMedicine.text = medicine
        binding.detailReminderDate.text = date
    }
}