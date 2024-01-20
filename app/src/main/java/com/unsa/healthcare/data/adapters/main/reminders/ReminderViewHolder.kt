package com.unsa.healthcare.data.adapters.main.reminders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.databinding.ItemReminderBinding

class ReminderViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemReminderBinding.bind(view)
    fun renderReminder(reminderEntity: ReminderEntity) {
        binding.itemReminderId.text = reminderEntity.id.toString()
        binding.itemReminderMedicine.text = reminderEntity.medicine
        binding.itemReminderDate.text = reminderEntity.date
    }
}