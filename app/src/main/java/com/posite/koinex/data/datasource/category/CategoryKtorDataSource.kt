package com.posite.koinex.data.datasource.category

import com.posite.koinex.data.remote.dto.category.CategoryKtorResponse
import com.posite.koinex.data.remote.service.MealKtorService
import com.posite.koinex.util.network.DataResult
import com.posite.koinex.util.network.handleKtorApi
import io.ktor.client.call.body

class CategoryKtorDataSource(private val service: MealKtorService) {
    suspend fun fetchCategories(): DataResult<CategoryKtorResponse> = handleKtorApi({
        runCatching {
            service.getCategories().body<CategoryKtorResponse>()
        }
    }) { it }
}