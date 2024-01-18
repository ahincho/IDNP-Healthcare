package com.unsa.healthcare.ui.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.domain.main.medicines.GetMedicinesUseCase
import com.unsa.healthcare.domain.main.reminders.GetRemindersUseCase
import com.unsa.healthcare.domain.main.reminders.InsertReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getMedicinesUseCase: GetMedicinesUseCase,
    private val getRemindersUseCase: GetRemindersUseCase,
    private val insertReminderUseCase: InsertReminderUseCase
) : ViewModel() {
    var medicines = MutableLiveData<MutableList<MedicineResponse>>()
    var reminders = MutableLiveData<MutableList<ReminderEntity>>()
    fun getMedicines() {
        viewModelScope.launch {
            medicines.postValue(getMedicinesUseCase.invoke())
        }
    }
    fun getReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            reminders.postValue(getRemindersUseCase.invoke())
        }
    }
    fun insertReminder(reminderEntity: ReminderEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertReminderUseCase.invoke(reminderEntity)
            reminders.postValue(getRemindersUseCase.invoke())
        }
    }
}