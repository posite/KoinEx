package com.posite.koinex.domain.model.meal

data class MealsModel(
    val meals: List<MealModel>
) {
    companion object {
        fun getEmptyMeals() = MealsModel(emptyList())
    }
}