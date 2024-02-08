package com.unsa.healthcare.di

import com.unsa.healthcare.core.Constants.Companion.REST_API_SERVICE
import com.unsa.healthcare.data.network.clients.AuthApiClient
import com.unsa.healthcare.data.network.clients.CategoryApiClient
import com.unsa.healthcare.data.network.clients.MedicineApiClient
import com.unsa.healthcare.data.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Builder {
        return Builder()
            .baseUrl(REST_API_SERVICE)
            .addConverterFactory(GsonConverterFactory.create())
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideAuthClient(retrofitBuilder: Builder): AuthApiClient {
        return retrofitBuilder.build().create(AuthApiClient::class.java)
    }
    @Singleton
    @Provides
    fun provideMedicineClient(retrofitBuilder: Builder, okHttpClient: OkHttpClient): MedicineApiClient {
        return retrofitBuilder.client(okHttpClient).build().create(MedicineApiClient::class.java)
    }
    @Singleton
    @Provides
    fun provideCategoryClient(retrofitBuilder: Builder, okHttpClient: OkHttpClient): CategoryApiClient {
        return retrofitBuilder.client(okHttpClient).build().create(CategoryApiClient::class.java)
    }
}