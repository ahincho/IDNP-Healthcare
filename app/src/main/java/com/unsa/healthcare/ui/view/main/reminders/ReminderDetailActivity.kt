package com.unsa.healthcare.ui.view.main.reminders

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.unsa.healthcare.R
import com.unsa.healthcare.core.Constants.Companion.REMINDER_STATUS_AWAITING
import com.unsa.healthcare.data.network.receivers.ReminderReceiver
import com.unsa.healthcare.data.network.receivers.ReminderReceiver.Companion.REMINDER_NOTIFICATION_TEXT
import com.unsa.healthcare.data.network.receivers.ReminderReceiver.Companion.REMINDER_NOTIFICATION_TITLE
import com.unsa.healthcare.core.checkDateInput
import com.unsa.healthcare.core.checkTextInput
import com.unsa.healthcare.core.checkTimeInput
import com.unsa.healthcare.core.recoverTextInput
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter.Companion.REMINDER_DATE
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter.Companion.REMINDER_DESCRIPTION
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter.Companion.REMINDER_ID
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter.Companion.REMINDER_MEDICINE
import com.unsa.healthcare.data.adapters.main.reminders.ReminderAdapter.Companion.REMINDER_TIME
import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.databinding.ActivityReminderDetailBinding
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ReminderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderDetailBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var id: Int? = 0
    private lateinit var medicine: String
    private lateinit var date: String
    private lateinit var time: String
    private lateinit var description: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        initReminderInfo()
        initListeners()
    }
    private fun initReminderInfo() {
        id = intent?.getIntExtra(REMINDER_ID, 0) ?: 0
        medicine = intent?.getStringExtra(REMINDER_MEDICINE) ?: ""
        date = intent?.getStringExtra(REMINDER_DATE) ?: ""
        time = intent?.getStringExtra(REMINDER_TIME) ?: ""
        description = intent?.getStringExtra(REMINDER_DESCRIPTION) ?: ""
        binding.reminderDetailsMedicine.text = Editable.Factory.getInstance().newEditable(medicine)
        binding.reminderDetailsDate.text = Editable.Factory.getInstance().newEditable(date)
        binding.reminderDetailsTime.text = Editable.Factory.getInstance().newEditable(time)
        binding.reminderDetailsDescription.text = Editable.Factory.getInstance().newEditable(description)
    }

    private fun initListeners() {
        binding.reminderDetailsDate.setOnClickListener { showDatePickerDialog() }
        binding.reminderDetailsTime.setOnClickListener { showTimePickerDialog() }
        binding.reminderDetailsCancel.setOnClickListener { finish() }
        binding.reminderDetailsSave.setOnClickListener { saveReminderInfo() }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (checkReminderWasEdited()) showExitConfirmationDialog() else finish()
        }
    }
    private fun saveReminderInfo() {
        if (reminderInputIsValid()) {
            val medicine = recoverTextInput(binding.reminderDetailsMedicine)
            val date = recoverTextInput(binding.reminderDetailsDate)
            val time = recoverTextInput(binding.reminderDetailsTime)
            val description = recoverTextInput(binding.reminderDetailsDescription)
            mainViewModel.insertReminder(Reminder(id ?: 0, medicine, date, time, description, REMINDER_STATUS_AWAITING))
            saveReminderAndScheduleNotification(Reminder(id ?: 0, medicine, date, time, description, REMINDER_STATUS_AWAITING))
            Toast.makeText(applicationContext, "Reminder was saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    @SuppressLint("ScheduleExactAlarm")
    private fun saveReminderAndScheduleNotification(reminder: Reminder) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(applicationContext, ReminderReceiver::class.java).apply {
            putExtra(REMINDER_NOTIFICATION_TITLE, reminder.medicine)
            putExtra(REMINDER_NOTIFICATION_TEXT, reminder.description)
        }
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, reminder.id, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val calendar = Calendar.getInstance().apply {
            val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dateTimeString = "${reminder.date} ${reminder.time}"
            time = dateTimeFormat.parse(dateTimeString) ?: Date()
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
    private fun checkReminderWasEdited(): Boolean {
        val isMedicineEdited = recoverTextInput(binding.reminderDetailsMedicine).equals(medicine, true)
        val isDateEdited = recoverTextInput(binding.reminderDetailsDate).equals(date, true)
        val isTimeEdited = recoverTextInput(binding.reminderDetailsTime).equals(time, true)
        val isDescriptionEdited = recoverTextInput(binding.reminderDetailsDescription).equals(description, true)
        return !(isMedicineEdited && isDateEdited && isTimeEdited && isDescriptionEdited)
    }
    private fun reminderInputIsValid(): Boolean {
        val isMedicineValid = checkTextInput(binding.reminderDetailsMedicine, binding.reminderDetailsMedicineHolder, getString(R.string.reminder_medicine_required))
        val isDateValid = checkDateInput(binding.reminderDetailsDate, binding.reminderDetailsDateHolder, getString(R.string.reminder_date_required))
        val isTimeValid = checkTimeInput(binding.reminderDetailsTime, binding.reminderDetailsTimeHolder, getString(R.string.reminder_time_required))
        val isDescriptionValid = checkTextInput(binding.reminderDetailsDescription, binding.reminderDetailsDescriptionHolder, getString(R.string.reminder_description_required))
        return isMedicineValid && isDateValid && isTimeValid && isDescriptionValid
    }
    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            binding.reminderDetailsDate.setText(formattedDate)
        }, year, month, day)
        datePickerDialog.show()
    }
    private fun showTimePickerDialog() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.reminderDetailsTime.setText(selectedTime)
        }, hour, minute, true)
        timePickerDialog.show()
    }
    fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Unsaved Changes")
        builder.setMessage("Are you sure you want to exit without saving changes?")
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}