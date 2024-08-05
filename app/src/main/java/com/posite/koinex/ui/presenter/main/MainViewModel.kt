package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.koinex.domain.usecase.GetCategoriesUseCase
import com.posite.koinex.ui.presenter.base.BaseViewModel
import com.posite.koinex.util.onSuccess
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: GetCategoriesUseCase) :
    BaseViewModel<MealContract.Event, MealContract.State, MealContract.Effect>() {
    override fun createInitialState(): MealContract.State {
        return MealContract.State(
            MealContract.MealListState.Before,
            MealContract.MealListState.Meals(emptyList())
        )
    }

    override fun handleEvent(event: MealContract.Event) {
        when (event) {
            is MealContract.Event.GetCategories -> {
                viewModelScope.launch {
                    setState { copy(loadState = MealContract.MealListState.Loading) }
                    Log.d("MainViewModel", "GetCategories")
                    useCase().collect { result ->
                        result.onSuccess { categories ->
                            Log.d("MainViewModel", "Success: $categories")
                            setState {
                                copy(
                                    loadState = MealContract.MealListState.Success,
                                    categories = MealContract.MealListState.Meals(categories.categories)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun getCategories() {
        setEvent(MealContract.Event.GetCategories)
    }

}