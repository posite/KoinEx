package com.posite.koinex.data.datasource.category

import com.posite.koinex.data.remote.dto.category.CategoryResponse
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.util.network.DataResult
import com.posite.koinex.util.network.handleApi

class CategoryDataSource(private val service: MealService) {
    suspend fun fetchCategories(): DataResult<CategoryResponse> {
        return handleApi({ service.getCategories() }) { it }
    }
}