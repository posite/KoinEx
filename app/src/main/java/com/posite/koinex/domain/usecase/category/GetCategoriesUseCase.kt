package com.posite.koinex.domain.usecase.category

import android.util.Log
import com.posite.koinex.data.remote.model.category.CategoryResponse
import com.posite.koinex.domain.repository.category.CategoryRepository
import com.posite.koinex.util.DataResult
import com.posite.koinex.util.onError
import com.posite.koinex.util.onException
import com.posite.koinex.util.onFail
import com.posite.koinex.util.onSuccess
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke() = flow<DataResult<CategoryResponse>> {
        try {
            emit(repository.getCategories().onSuccess {
                Log.d("GetCategoriesUseCase", "Success: $it")
                it
            }.onFail {
                Log.e("GetCategoriesUseCase", "Error: $it")
            }.onException {
                Log.e("GetCategoriesUseCase", "Exception: $it")
            }.onError {
                Log.e("GetCategoriesUseCase", "Error: $it")
            }
            )

        } catch (e: Exception) {

        }
    }
}