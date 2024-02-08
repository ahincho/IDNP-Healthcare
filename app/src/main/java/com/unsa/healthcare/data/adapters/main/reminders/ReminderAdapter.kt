package com.unsa.healthcare.data.adapters.main.reminders

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.R
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.ui.view.main.reminders.ReminderDetailActivity

class ReminderAdapter (
    private var reminders: List<Reminder>
): RecyclerView.Adapter<ReminderViewHolder>() {
    companion object {
        const val REMINDER_ID = "REMINDER_ID"
        const val REMINDER_MEDICINE = "REMINDER_MEDICINE"
        const val REMINDER_DATE = "REMINDER_DATE"
        const val REMINDER_TIME = "REMINDER_TIME"
        const val REMINDER_DESCRIPTION = "REMINDER_DESCRIPTION"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReminderViewHolder(layoutInflater.inflate(R.layout.item_reminder, parent, false))
    }
    override fun getItemCount(): Int {
        return reminders.size
    }
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.itemView.findViewById<CardView>(R.id.reminderCardView).setOnClickListener {
            val intent = Intent(it.context, ReminderDetailActivity::class.java)
            intent.putExtra(REMINDER_ID, reminder.id)
            intent.putExtra(REMINDER_MEDICINE, reminder.medicine)
            intent.putExtra(REMINDER_DATE, reminder.date)
            intent.putExtra(REMINDER_TIME, reminder.time)
            intent.putExtra(REMINDER_DESCRIPTION, reminder.description)
            it.context.startActivity(intent)
        }
        holder.renderReminder(reminder)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateReminders(reminders: List<Reminder>?) {
        this.reminders = reminders ?: emptyList()
        notifyDataSetChanged()
    }
}