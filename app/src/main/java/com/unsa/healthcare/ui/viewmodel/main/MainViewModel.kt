package com.unsa.healthcare.ui.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import com.unsa.healthcare.domain.main.medicines.GetMedicinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val getMedicinesUseCase: GetMedicinesUseCase,
) : ViewModel() {
    var medicines = MutableLiveData<MutableList<MedicineResponse>>()
    fun getMedicines() {
        viewModelScope.launch {
            medicines.postValue(getMedicinesUseCase.invoke())
        }
    }
}