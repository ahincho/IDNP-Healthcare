package com.unsa.healthcare.data.network.dtos.auth.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String,
    @SerializedName("username") val username: String
)