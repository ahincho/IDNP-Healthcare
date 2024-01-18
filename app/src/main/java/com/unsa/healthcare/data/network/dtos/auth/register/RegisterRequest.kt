package com.unsa.healthcare.data.network.dtos.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)