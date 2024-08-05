package com.posite.koinex.ui.presenter.main

sealed class MealScreen(val route: String) {
    object MealCategories : MealScreen("meal_categories")
    object MealDetail : MealScreen("meal_detail")

}