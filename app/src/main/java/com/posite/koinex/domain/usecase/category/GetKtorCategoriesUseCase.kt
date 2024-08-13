package com.posite.koinex.domain.usecase.category

import android.util.Log
import com.posite.koinex.domain.model.category.CategoriesModel
import com.posite.koinex.domain.repository.category.CategoryKtorRepository
import com.posite.koinex.util.network.DataResult
import com.posite.koinex.util.network.onError
import com.posite.koinex.util.network.onException
import com.posite.koinex.util.network.onFail
import com.posite.koinex.util.network.onSuccess
import kotlinx.coroutines.flow.flow

class GetKtorCategoriesUseCase(private val repository: CategoryKtorRepository) {
    suspend operator fun invoke() = flow<DataResult<CategoriesModel>> {
        try {
            emit(repository.getCategories().onSuccess {
                //Log.d("GetCategoriesUseCase", "Success: $it")
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