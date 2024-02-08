package com.unsa.healthcare.data.network.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.unsa.healthcare.data.network.receivers.ReminderReceiver.Companion.REMINDER_NOTIFICATION_ID
import com.unsa.healthcare.data.database.daos.ReminderDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TakenReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var reminderDao: ReminderDao
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAKEN BROADCAST", "TAKEN BROADCAST")
        context ?: return
        intent ?: return
        val notificationId = intent.getIntExtra(REMINDER_NOTIFICATION_ID, 1)
        Log.d("TAKEN BROADCAST", notificationId.toString())
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            reminderDao.updateReminderStatusToTaken(notificationId)
        }
    }
}