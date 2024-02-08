package com.unsa.healthcare.data.models

data class Reminder (
    val id: Int,
    val medicine: String,
    val date: String,
    val time: String,
    val description: String,
    val status: String
)