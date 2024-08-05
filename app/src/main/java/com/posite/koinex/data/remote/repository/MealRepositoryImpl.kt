package com.posite.koinex.data.remote.repository

import android.util.Log
import com.posite.koinex.data.datasource.MealDataSource
import com.posite.koinex.data.remote.model.CategoryResponse
import com.posite.koinex.domain.repository.MealRepository
import com.posite.koinex.util.DataResult

class MealRepositoryImpl(private val mealDataSource: MealDataSource) :
    MealRepository {
    override suspend fun getCategories(): DataResult<CategoryResponse> {
        Log.d("MealRepositoryImpl", "repository getCategories()")
        return mealDataSource.fetchCategories()
    }
}