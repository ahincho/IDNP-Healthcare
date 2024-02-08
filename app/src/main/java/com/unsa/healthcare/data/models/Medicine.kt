package com.unsa.healthcare.data.models

import android.graphics.Bitmap

data class Medicine (
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val image: Bitmap
)