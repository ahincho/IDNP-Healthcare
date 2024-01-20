package com.unsa.healthcare.data.network.dtos.auth.login

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)