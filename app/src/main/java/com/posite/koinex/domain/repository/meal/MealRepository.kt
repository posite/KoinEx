package com.posite.koinex.domain.repository.meal

import com.posite.koinex.data.remote.model.meal.MealResopnse
import com.posite.koinex.util.DataResult

interface MealRepository {
    suspend fun getMealsByCategory(category: String): DataResult<MealResopnse>
}