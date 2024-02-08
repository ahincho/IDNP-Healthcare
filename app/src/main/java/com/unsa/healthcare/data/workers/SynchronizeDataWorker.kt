package com.unsa.healthcare.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_CATEGORIES
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_MEDICINES
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_NO_CREDENTIALS
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_RETRYING_AUTH
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_RETRYING_FAILURE
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_RETRYING_REQUEST
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_RETRYING_RESOLVE_HOST
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_SUCCESS
import com.unsa.healthcare.core.Constants.Companion.SYNCHRONIZE_DATA_WORKER_TAG
import com.unsa.healthcare.core.toDatabase
import com.unsa.healthcare.data.database.daos.CategoryDao
import com.unsa.healthcare.data.database.daos.MedicineDao
import com.unsa.healthcare.data.network.clients.AuthApiClient
import com.unsa.healthcare.data.network.clients.CategoryApiClient
import com.unsa.healthcare.data.network.clients.MedicineApiClient
import com.unsa.healthcare.data.network.dtos.auth.login.LoginRequest
import com.unsa.healthcare.data.repositories.PreferencesRepository
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.JWT
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.PASSWORD
import com.unsa.healthcare.data.repositories.PreferencesRepository.Companion.USERNAME
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.UnknownHostException

@HiltWorker
class SynchronizeDataWorker @AssistedInject constructor (
    @Assisted private val preferencesRepository: PreferencesRepository,
    @Assisted private val authApiClient: AuthApiClient,
    @Assisted private val medicineApiClient: MedicineApiClient,
    @Assisted private val medicineDao: MedicineDao,
    @Assisted private val categoryApiClient: CategoryApiClient,
    @Assisted private val categoryDao: CategoryDao,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            val username = preferencesRepository.getPreference(USERNAME)
            val password = preferencesRepository.getPreference(PASSWORD)
            val token = preferencesRepository.getPreference(JWT)
            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                if (!token.isNullOrEmpty()) {
                    return synchronizeData()
                }
                return Result.failure(Data.Builder().putString(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_NO_CREDENTIALS).build())
            }
            val authResponse = authApiClient.login(LoginRequest(username, password))
            if (!authResponse.isSuccessful) {
                Log.d(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_RETRYING_AUTH)
                return Result.retry()
            }
            val jwt = authResponse.body()!!.token
            preferencesRepository.savePreference(JWT, jwt)
            return synchronizeData()
        } catch (exception: UnknownHostException) {
            Log.d(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_RETRYING_RESOLVE_HOST)
            return Result.retry()
        } catch (exception: Exception) {
            Log.d(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_RETRYING_FAILURE)
            return Result.failure(
                Data.Builder().putString(SYNCHRONIZE_DATA_WORKER_TAG, exception.toString()).build()
            )
        }
    }
    private suspend fun synchronizeData(): Result {
        val medicinesResponse = medicineApiClient.getAllMedicines()
        val categoriesResponse = categoryApiClient.getAllCategories()
        return if (medicinesResponse.isSuccessful && categoriesResponse.isSuccessful) {
            Log.d(SYNCHRONIZE_DATA_WORKER_TAG, String.format(SYNCHRONIZE_DATA_WORKER_MEDICINES, medicinesResponse.body()!!.size))
            medicinesResponse.body()?.map { medicineDao.insertMedicine(it.toDatabase(applicationContext)) }
            Log.d(SYNCHRONIZE_DATA_WORKER_TAG, String.format(SYNCHRONIZE_DATA_WORKER_CATEGORIES, categoriesResponse.body()!!.size))
            categoriesResponse.body()?.map { categoryDao.insertCategory(it.toDatabase()) }
            Result.success(Data.Builder().putString(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_SUCCESS).build())
        } else {
            Log.d(SYNCHRONIZE_DATA_WORKER_TAG, SYNCHRONIZE_DATA_WORKER_RETRYING_REQUEST)
            Result.retry()
        }
    }
}