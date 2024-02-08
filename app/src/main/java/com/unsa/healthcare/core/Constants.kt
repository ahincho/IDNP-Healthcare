package com.unsa.healthcare.core

class Constants {
    companion object {
        // Base URL of Rest API Service
        const val REST_API_SERVICE = "https://healthcare-backend-testing.onrender.com"
        const val AUTH_ENDPOINT = "/api/auth"
        const val MEDICINE_ENDPOINT = "/api/medicines"
        const val CATEGORY_ENDPOINT = "/api/categories"
        // Data Store Preferences
        const val DATA_STORE_PREFERENCES_NAME = "healthcarePreferences"
        // Room Local Database
        // Room Database Name
        const val DATABASE_NAME = "healthcare"
        // Room Medicines Table
        const val MEDICINES_TABLE = "medicines"
        const val MEDICINE_ID = "id"
        const val MEDICINE_NAME = "name"
        const val MEDICINE_CATEGORY = "category"
        const val MEDICINE_DESCRIPTION = "description"
        const val MEDICINE_IMAGE = "image"
        // Room Categories Table
        const val CATEGORIES_TABLE = "categories"
        const val CATEGORY_ID = "id"
        const val CATEGORY_NAME = "name"
        // Room Reminders Table
        const val REMINDERS_TABLE = "reminders"
        const val REMINDER_ID = "id"
        const val REMINDER_MEDICINE = "medicine"
        const val REMINDER_DATE = "date"
        const val REMINDER_TIME = "time"
        const val REMINDER_DESCRIPTION = "description"
        const val REMINDER_STATUS = "status"
        // Reminders Status
        const val REMINDER_STATUS_AWAITING = "Awaiting"
        const val REMINDER_STATUS_MISSED = "Missed"
        const val REMINDER_STATUS_TAKEN = "Taken"
        // Room Workouts Table
        const val WORKOUTS_TABLE = "workouts"
        const val WORKOUT_ID = "id"
        const val WORKOUT_DATE = "date"
        const val WORKOUT_ACTIVITY = "activity"
        const val WORKOUT_DURATION = "duration"
        // Work Manager Jobs
        const val SYNCHRONIZE_DATA_WORKER_TAG = "Synchronize Data Worker"
        const val SYNCHRONIZE_DATA_WORKER_NAME = "Synchronize Data"
        const val SYNCHRONIZE_DATA_WORKER_NO_CREDENTIALS = "User has not saved their credentials. Synchronization is not necessary!"
        const val SYNCHRONIZE_DATA_WORKER_RETRYING_REQUEST = "Retrying synchronization ..."
        const val SYNCHRONIZE_DATA_WORKER_RETRYING_RESOLVE_HOST = "Retrying to reach backend host ..."
        const val SYNCHRONIZE_DATA_WORKER_RETRYING_AUTH = "Retrying authentication ..."
        const val SYNCHRONIZE_DATA_WORKER_RETRYING_FAILURE = "Something went wrong!"
        const val SYNCHRONIZE_DATA_WORKER_MEDICINES = "Success! %d medicines will be updated!"
        const val SYNCHRONIZE_DATA_WORKER_CATEGORIES = "Success! %d categories will be updated!"
        const val SYNCHRONIZE_DATA_WORKER_SUCCESS = "Database was synchronized updated!"
    }
}