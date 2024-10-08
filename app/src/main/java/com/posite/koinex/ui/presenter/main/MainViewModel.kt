package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.koinex.domain.usecase.category.GetKtorCategoriesUseCase
import com.posite.koinex.ui.presenter.base.BaseViewModel
import com.posite.koinex.util.network.onSuccess
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: GetKtorCategoriesUseCase) :
    BaseViewModel<MainContract.MainEvent, MainContract.CategoryState, MainContract.CategoryEffect>() {
    override fun createInitialState(): MainContract.CategoryState {
        return MainContract.CategoryState(
            MainContract.CategoryListState.Before,
            MainContract.CategoryListState.Categories(emptyList())
        )
    }

    override fun handleEvent(mainEvent: MainContract.MainEvent) {
        viewModelScope.launch {
            when (mainEvent) {
                is MainContract.MainEvent.GetCategories -> {
                    setState { copy(loadState = MainContract.CategoryListState.Loading) }
                    Log.d("MainViewModel", "GetCategories")
                    useCase().collect { result ->
                        result.onSuccess { categories ->
                            Log.d("MainViewModel", "Success")
                            setState {
                                copy(
                                    loadState = MainContract.CategoryListState.Success,
                                    categories = MainContract.CategoryListState.Categories(
                                        categories.categories
                                    )
                                )
                            }
                        }
                    }

                }

                is MainContract.MainEvent.SetVisible -> {
                    setState { copy(loadState = MainContract.CategoryListState.Visible) }
                }

                is MainContract.MainEvent.SetInvisible -> {
                    setState { copy(loadState = MainContract.CategoryListState.Invisible) }
                }
            }
        }

    }

    fun getCategories() {
        setEvent(MainContract.MainEvent.GetCategories)
    }

    fun setVisible() {
        setEvent(MainContract.MainEvent.SetVisible)
    }

    fun setInvisible() {
        setEvent(MainContract.MainEvent.SetInvisible)
    }

}