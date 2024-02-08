package com.unsa.healthcare.data.network.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.unsa.healthcare.R

class ReminderReceiver : BroadcastReceiver() {
    companion object {
        const val REMINDER_NOTIFICATION_ID = "REMINDER_NOTIFICATION_ID"
        const val REMINDER_NOTIFICATION_TITLE = "REMINDER_NOTIFICATION_TITLE"
        const val REMINDER_NOTIFICATION_TEXT = "REMINDER_NOTIFICATION_TEXT"
        const val REMINDER_NOTIFICATION_CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL_ID"
        const val REMINDER_NOTIFICATION_CHANNEL_NAME = "REMINDER_NOTIFICATION_CHANNEL_NAME"
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val ACTION_TAKEN = "ACTION_TAKEN"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("REMINDER BROADCAST", "REMINDER BROADCAST")
        context ?: return
        intent ?: return
        val notificationId = intent.getIntExtra(REMINDER_NOTIFICATION_ID, 1)
        val notificationTitle = intent.getStringExtra(REMINDER_NOTIFICATION_TITLE) ?: ""
        val notificationText = intent.getStringExtra(REMINDER_NOTIFICATION_TEXT) ?: ""
        val dismissIntent = Intent(context, DismissReminderReceiver::class.java).apply {
            action = ACTION_DISMISS
            putExtra(REMINDER_NOTIFICATION_ID, notificationId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val takenIntent = Intent(context, TakenReminderReceiver::class.java).apply {
            action = ACTION_TAKEN
            putExtra(REMINDER_NOTIFICATION_ID, notificationId)
        }
        val takenPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            takenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(context)
        val notification = NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.healthcare)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_edit, "Dismiss", dismissPendingIntent)
            .addAction(R.drawable.ic_add, "Taken", takenPendingIntent)
            .build()
        notificationManager.notify(notificationId, notification)
        if (notificationTitle.isNotEmpty() && notificationText.isNotEmpty()) {
            println("Reminder Completed: $notificationTitle - $notificationText")
        }
    }
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                REMINDER_NOTIFICATION_CHANNEL_ID,
                REMINDER_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}