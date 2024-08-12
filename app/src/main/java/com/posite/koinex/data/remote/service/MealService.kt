package com.posite.koinex.data.remote.service

import com.posite.koinex.data.remote.dto.category.CategoryResponse
import com.posite.koinex.data.remote.dto.meal.MealResopnse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealResopnse>
}