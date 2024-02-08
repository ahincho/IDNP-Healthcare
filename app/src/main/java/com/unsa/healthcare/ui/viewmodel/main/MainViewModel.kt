package com.unsa.healthcare.ui.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.healthcare.data.models.Category
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.domain.main.categories.GetCategoriesUseCase
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
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRemindersUseCase: GetRemindersUseCase,
    private val insertReminderUseCase: InsertReminderUseCase
) : ViewModel() {
    val medicines = MutableLiveData<List<Medicine>>()
    val categories = MutableLiveData<List<Category>>()
    val reminders = MutableLiveData<MutableList<Reminder>>()
    fun getMedicines() {
        viewModelScope.launch(Dispatchers.IO) {
            medicines.postValue(getMedicinesUseCase.invoke())
        }
    }
    fun getCategories() {
        viewModelScope.launch {
            categories.postValue(getCategoriesUseCase.invoke())
        }
    }
    fun getReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            reminders.postValue(getRemindersUseCase.invoke())
        }
    }
    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            insertReminderUseCase.invoke(reminder)
            reminders.postValue(getRemindersUseCase.invoke())
        }
    }
}