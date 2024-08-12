package com.posite.koinex.ui.presenter.main

import com.posite.koinex.domain.model.meal.MealsModel
import com.posite.koinex.ui.presenter.base.UiEffect
import com.posite.koinex.ui.presenter.base.UiEvent
import com.posite.koinex.ui.presenter.base.UiState

class MealContract {
    sealed class MealEvent : UiEvent {
        data class GetMeals(val category: String) : MealEvent()
        object SetBefore : MealEvent()
        object SetVisible : MealEvent()
        object ClearAll : MealEvent()
    }

    sealed class MealListState {
        sealed class LoadState : MealListState() {
            object Before : LoadState()
            object Loading : LoadState()
            object Success : LoadState()
            object Visible : LoadState()
            object Invisible : LoadState()
        }

        data class Error(val message: String) : MealListState()
        data class Meals(val meals: MealsModel) : MealListState()
    }

    sealed class MealEffect : UiEffect {
        object ShowError : MealEffect()
    }

    data class MealState(
        val loadState: MealListState.LoadState,
        val meals: MealListState.Meals
    ) : UiState
}