package com.posite.koinex.data.remote.service

import com.posite.koinex.data.remote.model.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>
}