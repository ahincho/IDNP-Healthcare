package com.unsa.healthcare.ui.view.main.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.unsa.healthcare.R
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.databinding.DialogReminderAddBinding
import com.unsa.healthcare.ui.view.main.MainActivity
import com.unsa.healthcare.ui.viewmodel.main.MainViewModel

class ReminderAddDialog : DialogFragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: DialogReminderAddBinding
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogReminderAddBinding.inflate(layoutInflater)
        val mainActivity = activity as MainActivity
        mainViewModel = ViewModelProvider(mainActivity)[MainViewModel::class.java]
        initListeners()
        return binding.root
    }
    private fun initListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSubmit.setOnClickListener {
            if (reminderInputIsValid()) {
                val medicine = binding.createReminderMedicine.text.toString()
                val date = binding.createReminderDate.text.toString()
                mainViewModel.insertReminder(ReminderEntity(0, medicine, date))
                dismiss()
            }
        }
    }
    private fun reminderInputIsValid(): Boolean {
        val isMedicineValid = checkTextInput(binding.createReminderMedicine, binding.createReminderMedicineHolder, getString(R.string.reminder_medicine_required))
        val isDateValid = checkTextInput(binding.createReminderDate, binding.createReminderDateHolder, getString(R.string.reminder_date_required))
        return isMedicineValid && isDateValid
    }
    private fun checkTextInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
        val isValid = inputText.text.toString().isNotBlank()
        inputLayout.error = if (isValid) null else errorMessage
        return isValid
    }
}