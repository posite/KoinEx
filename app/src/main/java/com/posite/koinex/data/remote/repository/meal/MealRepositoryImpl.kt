package com.posite.koinex.data.remote.repository.meal

import com.posite.koinex.data.datasource.meal.MealDataSource
import com.posite.koinex.domain.repository.meal.MealRepository
import com.posite.koinex.util.mapper.MealMapper

class MealRepositoryImpl(
    private val mealDataSource: MealDataSource,
    private val mealMapper: MealMapper
) : MealRepository {
    override suspend fun getMealsByCategory(category: String) =
        mealMapper(mealDataSource.fetchMealsByCategory(category))
}