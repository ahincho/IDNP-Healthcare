package com.unsa.healthcare.data.network.clients

import com.unsa.healthcare.core.Constants.Companion.CATEGORY_ENDPOINT
import com.unsa.healthcare.data.network.dtos.main.categories.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApiClient {
    @GET(CATEGORY_ENDPOINT)
    suspend fun getAllCategories(): Response<List<CategoryResponse>>
}