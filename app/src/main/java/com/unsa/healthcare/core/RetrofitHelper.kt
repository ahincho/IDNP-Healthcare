package com.unsa.healthcare.core

import com.unsa.healthcare.core.Constants.Companion.REST_API_SERVICE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REST_API_SERVICE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}