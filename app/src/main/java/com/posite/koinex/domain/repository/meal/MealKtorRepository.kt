package com.posite.koinex.domain.repository.meal

import com.posite.koinex.domain.model.meal.MealsModel
import com.posite.koinex.util.network.DataResult

interface MealKtorRepository {
    suspend fun getMealsByCategory(category: String): DataResult<MealsModel>
}