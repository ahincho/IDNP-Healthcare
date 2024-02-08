package com.unsa.healthcare.ui.view.main

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unsa.healthcare.R
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_NAME
import com.unsa.healthcare.data.workers.SynchronizeDataWorker
import com.unsa.healthcare.databinding.ActivityMainBinding
import com.unsa.healthcare.ui.view.main.medicines.MedicineFragment
import com.unsa.healthcare.ui.view.main.reminders.ReminderFragment
import com.unsa.healthcare.ui.view.main.statistics.StatisticsFragment
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigation: BottomNavigationView
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace<MedicineFragment>(binding.mainContainer.id)
            setReorderingAllowed(true)
        }
        navigation = binding.mainNavMenu
        initListeners()
        initUserInterface()
        synchronizeMedicines()
    }
    private fun initListeners() {
        navListener()
    }
    private fun initUserInterface() {
        mainViewModel.getMedicines()
        mainViewModel.getReminders()
        mainViewModel.getCategories()
    }
    private fun synchronizeMedicines() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<SynchronizeDataWorker> (
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setBackoffCriteria (
            backoffPolicy = BackoffPolicy.LINEAR, 5, TimeUnit.MINUTES
        ).setConstraints(constraints).build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork (
            SYNCHRONIZE_DATA_WORKER_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
        workManager.getWorkInfosForUniqueWorkLiveData(SYNCHRONIZE_DATA_WORKER_NAME)
            .observe(this) {
                it.forEach { workInfo ->
                    Log.d(SYNCHRONIZE_DATA_WORKER_NAME, "Synchronize Data Worker: ${workInfo.state}")
                }
            }
    }
    private fun navListener() {
        navigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.itemMedicines -> MedicineFragment()
                R.id.itemReminders -> ReminderFragment()
                R.id.itemStatistics -> StatisticsFragment()
                else -> return@setOnItemSelectedListener false
            }
            val currentFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)
            if (currentFragment?.javaClass != fragment::class.java) {
                supportFragmentManager.commit {
                    replace(R.id.mainContainer, fragment)
                    setReorderingAllowed(true)
                }
            }
            true
        }
    }
}