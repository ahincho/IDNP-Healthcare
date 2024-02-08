package com.unsa.healthcare

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.unsa.healthcare.data.database.daos.CategoryDao
import com.unsa.healthcare.data.database.daos.MedicineDao
import com.unsa.healthcare.data.network.clients.AuthApiClient
import com.unsa.healthcare.data.network.clients.CategoryApiClient
import com.unsa.healthcare.data.network.clients.MedicineApiClient
import com.unsa.healthcare.data.repositories.PreferencesRepository
import com.unsa.healthcare.data.workers.SynchronizeDataWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HealthcareApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var synchronizeDataWorkerFactory: SynchronizeDataWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(synchronizeDataWorkerFactory)
            .build()
}
class SynchronizeDataWorkerFactory @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val authApiClient: AuthApiClient,
    private val medicineApiClient: MedicineApiClient,
    private val medicineDao: MedicineDao,
    private val categoryApiClient: CategoryApiClient,
    private val categoryDao: CategoryDao
) : WorkerFactory() {
    override fun createWorker (
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SynchronizeDataWorker (
        preferencesRepository, authApiClient, medicineApiClient, medicineDao,
        categoryApiClient, categoryDao, appContext, workerParameters
    )
}