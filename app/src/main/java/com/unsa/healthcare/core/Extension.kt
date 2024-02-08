package com.unsa.healthcare.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.unsa.healthcare.data.database.entities.CategoryEntity
import com.unsa.healthcare.data.database.entities.MedicineEntity
import com.unsa.healthcare.data.database.entities.ReminderEntity
import com.unsa.healthcare.data.models.Category
import com.unsa.healthcare.data.models.Medicine
import com.unsa.healthcare.data.models.Reminder
import com.unsa.healthcare.data.network.dtos.main.categories.CategoryResponse
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Medicine Mappers
suspend fun MedicineResponse.toDomain(context: Context) = Medicine(id, name, category, description, getBitmap(context, image))
suspend fun MedicineResponse.toDatabase(context: Context) = MedicineEntity(id, name, category, description, getBitmap(context, image))
fun MedicineEntity.toDomain() = Medicine(id, name, category, description, image)
fun Medicine.toDatabase() = MedicineEntity(id, name, category, description, image)
// Category Mappers
fun CategoryResponse.toDomain() = Category(id, name)
fun CategoryResponse.toDatabase() = CategoryEntity(id, name)
fun CategoryEntity.toDomain() = Category(id, name)
fun Category.toDatabase() = CategoryEntity(id, name)
// Reminder Mappers
fun ReminderEntity.toDomain() = Reminder(id, medicine, date, time, description, status)
fun Reminder.toDatabase() = ReminderEntity(id, medicine, date, time, description)
// Image Resource URL to Bitmap
suspend fun getBitmap(context: Context, url: String): Bitmap {
    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context).data(url).build()
    val result = (loading.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}
// Check if Input Text
fun checkTextInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
    val isValid = inputText.text.toString().isNotBlank()
    inputLayout.error = if (isValid) null else errorMessage
    return isValid
}
// Recover Text Input
fun recoverTextInput(inputEditText: TextInputEditText): String {
    return inputEditText.text.toString()
}
// Check if Date is Valid
fun checkDateInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
    val dateInput = inputText.text.toString().trim()
    if (dateInput.isEmpty()) {
        inputLayout.error = errorMessage
        return false
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    return try {
        dateFormat.parse(dateInput)
        inputLayout.error = null
        true
    } catch (e: Exception) {
        inputLayout.error = errorMessage
        false
    }
}
// Check if Time is Valid
fun checkTimeInput(inputText: TextInputEditText, inputLayout: TextInputLayout, errorMessage: String): Boolean {
    val timeInput = inputText.text.toString().trim()
    if (timeInput.isEmpty()) {
        inputLayout.error = errorMessage
        return false
    }
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    timeFormat.isLenient = false
    return try {
        timeFormat.parse(timeInput)
        inputLayout.error = null
        true
    } catch (e: Exception) {
        inputLayout.error = errorMessage
        false
    }
}