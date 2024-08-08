package com.posite.koinex.ui.presenter.main

sealed class Screens(val route: String) {
    object CategoryScreen : Screens("meal_categories")
    object MealScreen : Screens("meal_detail")

}