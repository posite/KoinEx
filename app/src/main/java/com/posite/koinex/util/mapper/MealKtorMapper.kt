package com.posite.koinex.util.mapper

import com.posite.koinex.data.remote.dto.meal.MealKtorResponse
import com.posite.koinex.domain.model.meal.MealModel
import com.posite.koinex.domain.model.meal.MealsModel
import com.posite.koinex.util.network.DataResult

class MealKtorMapper {
    operator fun invoke(data: DataResult<MealKtorResponse>) = changeInstanceType(data)

    fun changeInstanceType(instance: DataResult<MealKtorResponse>): DataResult<MealsModel> {
        return when (instance) {
            is DataResult.Success -> {
                DataResult.Success(
                    MealsModel(
                        instance.data.meals.map {
                            MealModel(
                                it.strMeal,
                                it.strMealThumb
                            )
                        }
                    )
                )
            }

            is DataResult.Fail -> {
                DataResult.Fail(instance.statusCode, instance.message)
            }

            is DataResult.Error -> {
                DataResult.Error(instance.exception)
            }

            is DataResult.Loading -> {
                DataResult.Loading()
            }
        }
    }
}