package com.posite.koinex.util.mapper

import com.posite.koinex.data.remote.dto.category.CategoryKtorResponse
import com.posite.koinex.domain.model.category.CategoriesModel
import com.posite.koinex.domain.model.category.CategoryModel
import com.posite.koinex.util.network.DataResult

class CategoryKtorMapper {
    operator fun invoke(data: DataResult<CategoryKtorResponse>) = changeInstanceType(data)

    fun changeInstanceType(instance: DataResult<CategoryKtorResponse>): DataResult<CategoriesModel> {
        return when (instance) {
            is DataResult.Success -> {
                DataResult.Success(
                    CategoriesModel(
                        instance.data.categories.map {
                            CategoryModel(
                                it.strCategory,
                                it.strCategoryThumb,
                                it.strCategoryDescription
                            )
                        }
                    )
                )
            }

            is DataResult.Fail -> {
                DataResult.Fail(instance.statusCode, instance.message)
            }

            is DataResult.Error -> {
                DataResult.Error(instance.exception)
            }

            is DataResult.Loading -> {
                DataResult.Loading()
            }
        }
    }
}