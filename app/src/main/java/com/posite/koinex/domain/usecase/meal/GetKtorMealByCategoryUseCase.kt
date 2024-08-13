package com.posite.koinex.domain.usecase.meal

import com.posite.koinex.domain.repository.meal.MealKtorRepository
import com.posite.koinex.util.network.onSuccess
import kotlinx.coroutines.flow.flow

class GetKtorMealByCategoryUseCase(private val repository: MealKtorRepository) {
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