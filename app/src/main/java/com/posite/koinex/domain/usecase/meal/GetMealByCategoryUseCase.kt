package com.posite.koinex.domain.usecase.meal

import com.posite.koinex.domain.repository.meal.MealRepository
import com.posite.koinex.util.onSuccess
import kotlinx.coroutines.flow.flow

class GetMealByCategoryUseCase(private val repository: MealRepository) {
    suspend operator fun invoke(category: String) = flow {
        try {
            emit(repository.getMealsByCategory(category).onSuccess {
                it
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}