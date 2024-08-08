package com.posite.koinex.ui.presenter.main

import com.posite.koinex.data.remote.model.meal.Meal
import com.posite.koinex.ui.presenter.base.UiEffect
import com.posite.koinex.ui.presenter.base.UiEvent
import com.posite.koinex.ui.presenter.base.UiState

class MealContract {
    sealed class MealEvent : UiEvent {
        data class GetMeals(val category: String) : MealEvent()
        object SetInit : MealEvent()
        object SetVisible : MealEvent()
        object ClearAll : MealEvent()
    }

    sealed class MealListState {
        sealed class LoadState : MealListState() {
            object Before : LoadState()
            object Loading : LoadState()
            object Success : LoadState()
            object Init : LoadState()
        }

        data class Visible(val visibility: Boolean) : MealListState()
        data class Error(val message: String) : MealListState()
        data class Meals(val meals: List<Meal>) : MealListState()
    }

    sealed class MealEffect : UiEffect {
        object ShowError : MealEffect()
    }

    data class MealState(
        val loadState: MealListState.LoadState,
        val visible: MealListState.Visible,
        val meals: MealListState.Meals
    ) : UiState
}