package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.koinex.domain.usecase.GetCategoriesUseCase
import com.posite.koinex.ui.presenter.base.BaseViewModel
import com.posite.koinex.util.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: GetCategoriesUseCase) :
    BaseViewModel<MealContract.Event, MealContract.State, MealContract.Effect>() {
    override fun createInitialState(): MealContract.State {
        return MealContract.State(
            MealContract.MealListState.Before,
            MealContract.MealListState.Visible(false),
            MealContract.MealListState.Meals(emptyList())
        )
    }

    override fun handleEvent(event: MealContract.Event) {
        viewModelScope.launch {
            when (event) {
                is MealContract.Event.GetCategories -> {
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

                is MealContract.Event.SetVisible -> {
                    delay(100)
                    setState { copy(visible = MealContract.MealListState.Visible(true)) }
                }
            }
        }

    }

    fun getCategories() {
        setEvent(MealContract.Event.GetCategories)
    }

    fun setVisible() {
        setEvent(MealContract.Event.SetVisible)
    }

}