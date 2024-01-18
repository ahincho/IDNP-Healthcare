package com.unsa.healthcare.data.network.dtos.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("username") val username: String
)