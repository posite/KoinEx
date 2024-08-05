package com.posite.koinex.domain.repository

import com.posite.koinex.data.remote.model.CategoryResponse
import com.posite.koinex.util.DataResult
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun getCategories(): DataResult<CategoryResponse>
}