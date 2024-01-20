package com.unsa.healthcare.di

import android.content.Context
import androidx.room.Room
import com.unsa.healthcare.core.Constants.Companion.DATABASE_NAME
import com.unsa.healthcare.data.database.HealthcareDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HealthcareDatabase::class.java, DATABASE_NAME).build()
    @Singleton
    @Provides
    fun provideReminderDao(database: HealthcareDatabase) = database.getReminderDao()
}