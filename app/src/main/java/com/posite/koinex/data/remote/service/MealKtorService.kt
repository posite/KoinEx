package com.posite.koinex.data.remote.service

import com.posite.koinex.KoinApplication.Companion.getString
import com.posite.koinex.R
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class MealKtorService(private val client: HttpClient) {


    suspend fun getCategories() = client.get(getString(R.string.category_url))

    suspend fun getMealsByCategory(category: String) =
        client.get {
            url(getString(R.string.meals_url))
            parameter("c", category)
        }
}