package com.unsa.healthcare.di

import android.content.Context
import com.unsa.healthcare.data.repositories.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {
    @Singleton
    @Provides
    fun provideDataStorePreferences (
        @ApplicationContext context: Context
    ): PreferencesRepository {
        return PreferencesRepository(context)
    }
}