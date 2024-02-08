package com.unsa.healthcare.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.unsa.healthcare.R
import java.lang.UnsupportedOperationException

class WorkoutForegroundService: Service() {
    companion object {
        private const val ONGOING_NOTIFICATION_ID = 101
        private const val TAG = "Workout Foreground Service"
        fun startService(context: Context) {
            val intent = Intent(context, WorkoutForegroundService::class.java)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                context.startService(intent)
            } else {
                context.startForegroundService(intent)
            }
        }
        fun stopService(context: Context) {
            val intent = Intent(context, WorkoutForegroundService::class.java)
            context.stopService(intent)
        }
    }
    private lateinit var notificationManager: NotificationManager
    private var isStarted = false
    private var serviceStartTime: Long = 0
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    override fun onDestroy() {
        super.onDestroy()
        isStarted = false
        // Calcula el tiempo que estuvo vivo el servicio y registra en Logcat
        val elapsedTime = SystemClock.elapsedRealtime() - serviceStartTime
        Log.d(TAG, "Service was live for ${elapsedTime}ms")
    }
    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isStarted) {
            makeForeground()
            isStarted = true
            serviceStartTime = SystemClock.elapsedRealtime()
        }
        return START_STICKY
    }
    private fun makeForeground() {
        createServiceNotificationChannel()
        val notification = NotificationCompat.Builder(this, "Foreground")
            .setContentTitle("Foreground Service Title")
            .setContentText("Foreground Service Text")
            .setSmallIcon(R.drawable.medicine)
            .build()
        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }
    private fun createServiceNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val channel = NotificationChannel(
            "Foreground",
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }
}