package com.posite.koinex.ui.presenter.main

import androidx.lifecycle.viewModelScope
import com.posite.koinex.domain.usecase.meal.GetMealByCategoryUseCase
import com.posite.koinex.ui.presenter.base.BaseViewModel
import com.posite.koinex.util.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MealViewModel(private val getMealsUseCase: GetMealByCategoryUseCase) :
    BaseViewModel<MealContract.MealEvent, MealContract.MealState, MealContract.MealEffect>() {
    override fun createInitialState(): MealContract.MealState {
        return MealContract.MealState(
            MealContract.MealListState.LoadState.Before,
            MealContract.MealListState.Visible(false),
            MealContract.MealListState.Meals(emptyList())
        )
    }

    override fun handleEvent(event: MealContract.MealEvent) {
        viewModelScope.launch {
            when (event) {
                is MealContract.MealEvent.GetMeals -> {
                    getMealsUseCase(event.category).collect { result ->
                        result.onSuccess { mealResponse ->
                            setState {
                                copy(
                                    loadState = MealContract.MealListState.LoadState.Success,
                                    meals = MealContract.MealListState.Meals(mealResponse.meals)
                                )
                            }
                        }
                    }
                }

                is MealContract.MealEvent.SetVisible -> {
                    delay(100)
                    setState { copy(visible = MealContract.MealListState.Visible(true)) }
                }

                is MealContract.MealEvent.ClearAll -> {
                    setState {
                        copy(
                            loadState = MealContract.MealListState.LoadState.Before,
                            visible = MealContract.MealListState.Visible(false),
                            meals = MealContract.MealListState.Meals(emptyList())
                        )
                    }
                }
            }
        }
    }

    fun getMeals(category: String) {
        setEvent(MealContract.MealEvent.GetMeals(category))
    }

    fun setVisible() {
        setEvent(MealContract.MealEvent.SetVisible)
    }

    fun clearAll() {
        setEvent(MealContract.MealEvent.ClearAll)
    }
}
