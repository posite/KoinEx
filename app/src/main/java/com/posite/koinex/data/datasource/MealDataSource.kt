package com.posite.koinex.data.datasource

import com.posite.koinex.data.remote.model.CategoryResponse
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.util.DataResult
import com.posite.koinex.util.handleApi

class MealDataSource(private val service: MealService) {
    suspend fun fetchCategories(): DataResult<CategoryResponse> {
        return handleApi({ service.getCategories() }) { it }
    }
}