package com.posite.koinex.data.remote.repository.category

import android.util.Log
import com.posite.koinex.data.datasource.category.CategoryDataSource
import com.posite.koinex.data.remote.dto.category.CategoryResponse
import com.posite.koinex.domain.repository.category.CategoryRepository
import com.posite.koinex.util.network.DataResult

class CategoryRepositoryImpl(private val categoryDataSource: CategoryDataSource) :
    CategoryRepository {
    override suspend fun getCategories(): DataResult<CategoryResponse> {
        Log.d("MealRepositoryImpl", "repository getCategories()")
        return categoryDataSource.fetchCategories()
    }
}