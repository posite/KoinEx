package com.posite.koinex.data.datasource.meal

import com.posite.koinex.data.remote.dto.meal.MealResopnse
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.util.network.DataResult
import com.posite.koinex.util.network.handleApi

class MealDataSource(private val service: MealService) {
    suspend fun fetchMealsByCategory(category: String): DataResult<MealResopnse> {
        return handleApi({ service.getMealsByCategory(category) }) { it }
    }
}