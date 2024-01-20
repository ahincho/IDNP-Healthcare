package com.unsa.healthcare.core

class Constants {
    companion object {
        // Base URL of Rest API Service
        const val REST_API_SERVICE = "https://healthcare-backend-testing.onrender.com"
        const val AUTH_ENDPOINT = "/api/auth"
        const val MEDICINE_ENDPOINT = "/api/medicines"
        const val CATEGORY_ENDPOINT = "/api/categories"
        // Room Local Database
        // Room Database Name
        const val DATABASE_NAME = "healthcare"
        // Room Reminders Table
        const val REMINDERS_TABLE = "reminders"
        const val REMINDER_ID = "id"
        const val REMINDER_MEDICINE = "medicine"
        const val REMINDER_DATE = "date"
    }
}