package com.unsa.healthcare.data.adapters.main.reminders

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.unsa.healthcare.R
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.ui.view.main.reminders.ReminderDetailActivity

class ReminderAdapter (
    private var reminders: List<ReminderEntity>
): RecyclerView.Adapter<ReminderViewHolder>() {
    companion object {
        const val REMINDER_ID = "REMINDER_ID"
        const val REMINDER_MEDICINE = "REMINDER_MEDICINE"
        const val REMINDER_DATE = "REMINDER_DATE"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReminderViewHolder(layoutInflater.inflate(R.layout.item_reminder, parent, false))
    }
    override fun getItemCount(): Int {
        return reminders.size
    }
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val remindersEntity = reminders[position]
        holder.itemView.findViewById<CardView>(R.id.reminderCardView).setOnClickListener {
            val intent = Intent(it.context, ReminderDetailActivity::class.java)
            intent.putExtra(REMINDER_ID, reminders[position].id)
            intent.putExtra(REMINDER_MEDICINE, reminders[position].medicine)
            intent.putExtra(REMINDER_DATE, reminders[position].date)
            it.context.startActivity(intent)
        }
        holder.renderReminder(remindersEntity)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateReminders(reminders: List<ReminderEntity>?) {
        this.reminders = reminders ?: emptyList()
        notifyDataSetChanged()
    }
}