package com.posite.koinex.data.datasource.meal

import com.posite.koinex.data.remote.model.meal.MealResopnse
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.util.DataResult
import com.posite.koinex.util.handleApi

class MealDataSource(private val service: MealService) {
    suspend fun fetchMealsByCategory(category: String): DataResult<MealResopnse> {
        return handleApi({ service.getMealsByCategory(category) }) { it }
    }
}