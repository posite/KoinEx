package com.posite.koinex.data.remote.repository.meal

import com.posite.koinex.data.datasource.meal.MealKtorDataSource
import com.posite.koinex.domain.model.meal.MealsModel
import com.posite.koinex.domain.repository.meal.MealKtorRepository
import com.posite.koinex.util.mapper.MealKtorMapper
import com.posite.koinex.util.network.DataResult

class MealKtorRepositoryImpl(
    private val mealDataSource: MealKtorDataSource,
    private val mapper: MealKtorMapper
) : MealKtorRepository {
    override suspend fun getMealsByCategory(category: String): DataResult<MealsModel> {
        return mapper(mealDataSource.fetchMealsByCategory(category))
    }
}