package com.posite.koinex.ui.presenter.main

import com.posite.koinex.data.remote.dto.category.CategoryDto
import com.posite.koinex.ui.presenter.base.UiEffect
import com.posite.koinex.ui.presenter.base.UiEvent
import com.posite.koinex.ui.presenter.base.UiState

class MainContract {
    sealed class MainEvent : UiEvent {
        object GetCategories : MainEvent()
        object SetVisible : MainEvent()
        object SetInvisible : MainEvent()
    }

    sealed class CategoryListState {
        object Before : CategoryListState()
        object Loading : CategoryListState()
        object Success : CategoryListState()
        object Visible : CategoryListState()
        object Invisible : CategoryListState()
        data class Error(val message: String) : CategoryListState()
        data class Categories(val categories: List<CategoryDto>) : CategoryListState()
    }

    sealed class CategoryEffect : UiEffect {
        object ShowError : CategoryEffect()
    }

    data class CategoryState(
        val loadState: CategoryListState,
        val categories: CategoryListState.Categories
    ) : UiState

}