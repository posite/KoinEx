package com.posite.koinex.domain.repository.category

import com.posite.koinex.data.remote.model.category.CategoryResponse
import com.posite.koinex.util.DataResult

interface CategoryRepository {
    suspend fun getCategories(): DataResult<CategoryResponse>
}