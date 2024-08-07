package com.posite.koinex.ui.presenter.main

import com.posite.koinex.data.remote.model.Category
import com.posite.koinex.ui.presenter.base.UiEffect
import com.posite.koinex.ui.presenter.base.UiEvent
import com.posite.koinex.ui.presenter.base.UiState

class MealContract {
    sealed class Event : UiEvent {
        object GetCategories : Event()
        object SetVisible : Event()
    }

    sealed class MealListState {
        object Before : MealListState()
        object Loading : MealListState()
        object Success : MealListState()
        data class Visible(val visibility: Boolean) : MealListState()
        data class Error(val message: String) : MealListState()
        data class Meals(val categories: List<Category>) : MealListState()
    }

    sealed class Effect : UiEffect {
        object ShowError : Effect()
    }

    data class State(
        val loadState: MealListState,
        val visible: MealListState.Visible,
        val categories: MealListState.Meals
    ) : UiState

}