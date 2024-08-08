package com.posite.koinex.data.remote.repository.meal

import com.posite.koinex.data.datasource.meal.MealDataSource
import com.posite.koinex.domain.repository.meal.MealRepository

class MealRepositoryImpl(private val mealDataSource: MealDataSource) : MealRepository {
    override suspend fun getMealsByCategory(category: String) =
        mealDataSource.fetchMealsByCategory(category)
}