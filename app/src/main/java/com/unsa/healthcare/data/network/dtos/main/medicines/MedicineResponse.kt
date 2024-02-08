package com.unsa.healthcare.data.network.dtos.main.medicines

import com.google.gson.annotations.SerializedName

data class MedicineResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageUrl") val image: String
)