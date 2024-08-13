package com.posite.koinex.data.remote.dto.meal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealKtorDto(
    @SerialName("idMeal")
    val idMeal: String,
    @SerialName("strMeal")
    val strMeal: String,
    @SerialName("strMealThumb")
    val strMealThumb: String
)