package com.unsa.healthcare.domain.main.categories

import com.unsa.healthcare.data.models.Category
import com.unsa.healthcare.data.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor (
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return categoryRepository.getCategoriesFromDatabase()
    }
}