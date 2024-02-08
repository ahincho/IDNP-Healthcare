package com.unsa.healthcare.data.repositories

import com.unsa.healthcare.core.toDatabase
import com.unsa.healthcare.core.toDomain
import com.unsa.healthcare.data.database.daos.CategoryDao
import com.unsa.healthcare.data.models.Category
import com.unsa.healthcare.data.network.clients.CategoryApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor (
    private val categoryDao: CategoryDao,
    private val categoryApiClient: CategoryApiClient
) {
    suspend fun getCategoriesFromApi(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = categoryApiClient.getAllCategories().body()
            response?.map { it.toDomain() } ?: emptyList()
        }
    }
    suspend fun getCategoriesFromDatabase(): List<Category> {
        return withContext(Dispatchers.IO) {
            val categories = categoryDao.getAllCategories()
            categories.map { it.toDomain() }
        }
    }
    suspend fun insertCategoryOnDatabase(category: Category) {
        return withContext(Dispatchers.IO) {
            categoryDao.insertCategory(category.toDatabase())
        }
    }
}