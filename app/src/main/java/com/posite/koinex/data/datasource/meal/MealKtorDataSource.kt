package com.posite.koinex.data.datasource.meal

import com.posite.koinex.data.remote.dto.meal.MealKtorResponse
import com.posite.koinex.data.remote.service.MealKtorService
import com.posite.koinex.util.network.DataResult
import com.posite.koinex.util.network.handleKtorApi
import io.ktor.client.call.body

class MealKtorDataSource(private val service: MealKtorService) {
    suspend fun fetchMealsByCategory(category: String): DataResult<MealKtorResponse> {
        return handleKtorApi({
            runCatching {
                service.getMealsByCategory(category).body<MealKtorResponse>()
            }
        }) { it }
    }
}