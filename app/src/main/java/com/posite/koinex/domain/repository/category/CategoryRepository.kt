package com.posite.koinex.domain.repository.category

import com.posite.koinex.data.remote.dto.category.CategoryResponse
import com.posite.koinex.util.network.DataResult

interface CategoryRepository {
    suspend fun getCategories(): DataResult<CategoryResponse>
}