package com.unsa.healthcare.data.adapters.main.reminders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.databinding.ItemReminderBinding

class ReminderViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemReminderBinding.bind(view)
    fun renderReminder(reminder: Reminder) {
        binding.itemReminderMedicine.text = reminder.medicine
        "${reminder.date} ${reminder.time}".also { binding.itemReminderDatetime.text = it }
        binding.itemReminderDescription.text = reminder.description
        binding.itemReminderStatus.text = reminder.status
    }
}