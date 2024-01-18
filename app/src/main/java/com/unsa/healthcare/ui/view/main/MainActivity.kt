package com.unsa.healthcare.ui.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unsa.healthcare.R
import com.unsa.healthcare.databinding.ActivityMainBinding
import com.unsa.healthcare.ui.view.main.medicines.MedicineFragment
import com.unsa.healthcare.ui.view.main.reminders.ReminderFragment
import com.unsa.healthcare.ui.view.main.statistics.StatisticsFragment
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    }
    private fun initListeners() {
        navListener()
    }
    private fun initUserInterface() {
        mainViewModel.getMedicines()
    }
    private fun navListener() {
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemMedicines -> {
                    supportFragmentManager.commit {
                        replace<MedicineFragment>(R.id.mainContainer)
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.itemReminders -> {
                    supportFragmentManager.commit {
                        replace<ReminderFragment>(R.id.mainContainer)
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.itemStatistics -> {
                    supportFragmentManager.commit {
                        replace<StatisticsFragment>(R.id.mainContainer)
                        setReorderingAllowed(true)
                    }
                    true
                }
                else -> true
            }
        }
    }
}