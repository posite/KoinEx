package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.koinex.domain.model.meal.MealsModel
import com.posite.koinex.domain.usecase.meal.GetMealByCategoryUseCase
import com.posite.koinex.ui.presenter.base.BaseViewModel
import com.posite.koinex.util.network.onSuccess
import kotlinx.coroutines.launch

class MealViewModel(private val getMealsUseCase: GetMealByCategoryUseCase) :
    BaseViewModel<MealContract.MealEvent, MealContract.MealState, MealContract.MealEffect>() {
    override fun createInitialState(): MealContract.MealState {
        return MealContract.MealState(
            MealContract.MealListState.LoadState.Before,
            MealContract.MealListState.Meals(MealsModel.getEmptyMeals())
        )
    }

    override fun handleEvent(event: MealContract.MealEvent) {
        viewModelScope.launch {
            when (event) {
                is MealContract.MealEvent.GetMeals -> {
                    setState { copy(loadState = MealContract.MealListState.LoadState.Loading) }
                    getMealsUseCase(event.category).collect { result ->
                        result.onSuccess { meals ->
                            setState {
                                copy(
                                    loadState = MealContract.MealListState.LoadState.Success,
                                    meals = MealContract.MealListState.Meals(meals = meals)
                                )
                            }
                        }
                    }
                }

                is MealContract.MealEvent.SetVisible -> {
                    setState { copy(loadState = MealContract.MealListState.LoadState.Visible) }
                }

                is MealContract.MealEvent.ClearAll -> {
                    Log.d("MealViewModel", "ClearAll")
                    setState {
                        copy(
                            loadState = MealContract.MealListState.LoadState.Invisible,
                            meals = MealContract.MealListState.Meals(MealsModel.getEmptyMeals())
                        )
                    }
                }

                is MealContract.MealEvent.SetBefore -> {
                    setState {
                        copy(
                            loadState = MealContract.MealListState.LoadState.Before,
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

    fun setBefore() {
        setEvent(MealContract.MealEvent.SetBefore)
    }
}
