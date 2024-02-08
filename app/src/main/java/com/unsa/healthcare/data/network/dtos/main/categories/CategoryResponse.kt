package com.unsa.healthcare.data.network.dtos.main.categories

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)