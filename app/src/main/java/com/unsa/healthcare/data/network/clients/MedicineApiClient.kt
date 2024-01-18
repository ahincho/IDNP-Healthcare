package com.unsa.healthcare.data.network.clients

import com.unsa.healthcare.core.Constants.Companion.MEDICINE_ENDPOINT
import com.unsa.healthcare.data.network.dtos.main.medicines.MedicineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MedicineApiClient {
    @GET(MEDICINE_ENDPOINT)
    suspend fun getAll(): Response<MutableList<MedicineResponse>>
    @GET("${MEDICINE_ENDPOINT}/{id}")
    suspend fun getById(@Path("id") id: Int): Response<MedicineResponse>
}