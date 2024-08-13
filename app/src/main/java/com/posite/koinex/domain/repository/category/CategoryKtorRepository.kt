package com.posite.koinex.domain.repository.category

import com.posite.koinex.domain.model.category.CategoriesModel
import com.posite.koinex.util.network.DataResult

interface CategoryKtorRepository {
    suspend fun getCategories(): DataResult<CategoriesModel>
}